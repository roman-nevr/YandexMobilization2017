package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class Interactor<Response, Request> {

    @Inject ThreadPoolExecutor workExecutor;
    @Inject Scheduler mainExecutor;

    private CompositeDisposable disposable = new CompositeDisposable();

    protected abstract Observable<Response> buildObservable(Request param);

    /**
     * Executes the current interactor on background.
     *
     * @param observer {@link DisposableObserver} which will be listening to the
     * observable build by {@link #buildObservable(Request)} () method.
     * null if don't need listen to events
     * @param param Parameter which need to to {@link #buildObservable(Request)} () method.
     */
    public void execute(@Nullable DisposableObserver<Response> observer, Request param) {
        if(observer == null){
            observer = new EmptyObserver();
        }
        Observable<Response> observable = buildObservable(param)
                .subscribeOn(Schedulers.io())
                .observeOn(mainExecutor);
        disposable.add(observable.subscribeWith(observer));
    }

    /**
     * Executes the current interactor on main thread.
     *
     * @param param Parameter which need to to {@link #buildObservable(Request)} ()} method.
     */

    public Observable<Response> execute(Request param){
        return buildObservable(param);
    }

    public void dispose(){
        disposable.clear();
    }

    private class EmptyObserver extends DisposableObserver<Response>{

        @Override public void onNext(Response response) {
        }

        @Override public void onError(Throwable e) {
        }

        @Override public void onComplete() {
            disposable.clear();
        }
    }
}
