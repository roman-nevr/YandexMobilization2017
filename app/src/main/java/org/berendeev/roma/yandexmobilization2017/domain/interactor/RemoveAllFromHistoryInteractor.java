package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class RemoveAllFromHistoryInteractor extends Interactor<Void, Void> {

    @Inject HistoryAndFavouritesRepository repository;

    @Inject
    public RemoveAllFromHistoryInteractor() {}

    @Override protected Observable<Void> buildObservable(Void param) {
        return repository.removeAllFromHistory().toObservable();
    }

    public Disposable execute() {
        return execute(new VoidObserver(), null);
    }
}
