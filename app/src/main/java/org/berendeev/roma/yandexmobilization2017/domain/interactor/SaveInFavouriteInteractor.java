package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SaveInFavouriteInteractor extends Interactor<Void, Word> {
    @Inject HistoryAndFavouritesRepository repository;

    @Inject
    public SaveInFavouriteInteractor() {}

    @Override protected Observable<Void> buildObservable(Word param) {
        return repository.saveInFavourites(param).toObservable();
    }
}
