package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CheckIfFavouriteInteractor extends Interactor<Word, Word> {

    @Inject HistoryAndFavouritesRepository repository;

    @Inject
    public CheckIfFavouriteInteractor() {}

    @Override protected Observable<Word> buildObservable(Word param) {
        return repository.checkIfInFavourites(param);
    }
}
