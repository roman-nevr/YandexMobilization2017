package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import io.reactivex.observers.DisposableObserver;

public class VoidObserver extends DisposableObserver<Void> {

    public VoidObserver() {}

    @Override public void onNext(Void aVoid) {

    }

    @Override public void onError(Throwable e) {

    }

    @Override public void onComplete() {
        dispose();
    }
}
