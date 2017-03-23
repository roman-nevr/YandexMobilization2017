package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SetDirectionToInteractor extends Interactor<Void, String> {

    @Inject PreferencesRepository repository;

    @Inject
    public SetDirectionToInteractor() {}

    @Override protected Observable<Void> buildObservable(String param) {
        return repository.setDirectionTo(param).toObservable();
    }
}
