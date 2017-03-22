package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class Interactor<Response, Request> {

    @Inject ThreadPoolExecutor workExecutor;
    @Inject Scheduler mainExecutor;

    CompositeDisposable disposable = new CompositeDisposable();

    public void execute(DisposableObserver<Response> observer, Request param) {
        Observable<Response> observable = buildObservable(param)
                .subscribeOn(Schedulers.io())
                .observeOn(mainExecutor);
        disposable.add (observable.subscribeWith(observer));
    }

    public void dispose(){
        disposable.clear();
    }

    protected abstract Observable<Response> buildObservable(Request param);
}
