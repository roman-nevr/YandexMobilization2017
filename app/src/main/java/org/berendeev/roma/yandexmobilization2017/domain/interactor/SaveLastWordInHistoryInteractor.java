package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SaveLastWordInHistoryInteractor extends Interactor<Void, Void> {

    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;
    @Inject ResultRepository resultRepository;

    @Inject
    public SaveLastWordInHistoryInteractor() {}

    @Override public Observable<Void> buildObservable(Void param) {
        return resultRepository
                .getResultObservable()
                .firstElement()
                .flatMapCompletable(word -> historyAndFavouritesRepository
                        .saveInHistory(word))
                .toObservable();
    }
}
