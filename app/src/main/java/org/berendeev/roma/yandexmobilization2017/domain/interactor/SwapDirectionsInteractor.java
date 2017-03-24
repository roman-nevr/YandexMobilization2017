package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SwapDirectionsInteractor extends Interactor<Void, Void> {

    @Inject PreferencesRepository repository;

    @Inject
    public SwapDirectionsInteractor() {}

    @Override protected Observable<Void> buildObservable(Void param) {
        return repository.swapDirections().toObservable();
    }
}
