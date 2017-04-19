package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class Interactor<Response, Request> {

    //Наследники этого класса являются правилами бизнес-логики
    //В данном классе

    @Inject ThreadPoolExecutor workExecutor;
    @Inject Scheduler mainExecutor;

    protected abstract Observable<Response> buildObservable(Request param);

    /**
     * Executes the current interactor on background.
     *
     * @param observer {@link DisposableObserver} which will be listening to the
     * observable build by {@link #buildObservable(Request)} () method.
     * null if don't need listen to events
     * @param param Parameter which need to to {@link #buildObservable(Request)} () method.
     */
    //запуск интерактора на выполнение асинхронно
    public Disposable execute(@Nullable DisposableObserver<Response> observer, Request param) {
        if(observer == null){
            observer = new EmptyObserver();
        }
        Observable<Response> observable = buildObservable(param)
                .subscribeOn(Schedulers.from(workExecutor))
//                .subscribeOn(Schedulers.io())
                .observeOn(mainExecutor);
       return observable.subscribeWith(observer);
    }

    /**
     * Executes the current interactor on main thread.
     *
     * @param param Parameter which need to to {@link #buildObservable(Request)} ()} method.
     */
    //можно запустить и синхронно (иногда очень важно выполнить быструю операция синхронно)
    public Observable<Response> execute(Request param){
        return buildObservable(param);
    }

    //Нам не всегда нужен результат выполнения интерактора, а интерактор без обзервера не выполнится
    //поэтому используем этот пустой обзервер
    private class EmptyObserver extends DisposableObserver<Response>{

        @Override public void onNext(Response response) {
        }

        @Override public void onError(Throwable e) {
        }

        @Override public void onComplete() {
        }
    }
}
