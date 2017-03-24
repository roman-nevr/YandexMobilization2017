package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RemoveFromHistoryInteractor extends Interactor<Void, Word> {

    @Inject HistoryAndFavouritesRepository repository;

    @Inject
    public RemoveFromHistoryInteractor() {}

    @Override protected Observable<Void> buildObservable(Word param) {
        return repository.removeFromHistory(param).toObservable();
    }
}
