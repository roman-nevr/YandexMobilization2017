package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class RemoveAllFromFavouritesInteractor extends Interactor<Void, Void> {

    @Inject HistoryAndFavouritesRepository repository;

    @Inject
    public RemoveAllFromFavouritesInteractor() {}

    @Override protected Observable<Void> buildObservable(Void param) {
        return repository.removeAllFromFavourites().toObservable();
    }

    public Disposable execute() {
        return execute(new VoidObserver(), null);
    }
}
