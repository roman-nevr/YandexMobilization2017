package org.berendeev.roma.yandexmobilization2017.rx;

import java.util.concurrent.Callable;

import io.reactivex.Single;

public abstract class LongSingle<T> {
    //обычный Single.fromCallable не проверяет отписан ли подписчик
    public static <T> Single<T> fromCallable(final Callable<? extends T> callable){
        return Single.create(emitter -> {
            try {
                T result = callable.call();
                if (!emitter.isDisposed()){
                    emitter.onSuccess(result);
                }
            }catch (Throwable throwable){
                if (!emitter.isDisposed()){
                    emitter.onError(throwable);
                }
            }
        });
    }
}
