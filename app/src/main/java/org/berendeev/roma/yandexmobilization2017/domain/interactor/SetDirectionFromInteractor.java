package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SetDirectionFromInteractor extends Interactor<Void, String> {

    @Inject ResultRepository repository;

    @Inject
    public SetDirectionFromInteractor() {}

    @Override public Observable<Void> buildObservable(String param) {
        return repository
                .setDirectionFrom(param)
                .andThen(repository
                        .invalidateResult())
                .toObservable();
    }
}
