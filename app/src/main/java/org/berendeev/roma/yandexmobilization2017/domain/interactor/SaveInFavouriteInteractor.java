package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SaveInFavouriteInteractor extends Interactor<Void, Word> {
    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;

    @Inject
    public SaveInFavouriteInteractor() {}

    @Override public Observable<Void> buildObservable(Word param) {
        return historyAndFavouritesRepository.saveInFavourites(param).toObservable();
    }
}
