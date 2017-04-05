package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RemoveFromFavouritesInteractor extends Interactor<Void, Word> {

    @Inject HistoryAndFavouritesRepository repository;

    @Inject
    public RemoveFromFavouritesInteractor() {}

    @Override public Observable<Void> buildObservable(Word param) {
        return repository.removeFromFavourites(param).toObservable();
    }
}
