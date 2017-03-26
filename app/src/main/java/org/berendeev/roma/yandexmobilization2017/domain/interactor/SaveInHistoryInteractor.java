package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class SaveInHistoryInteractor extends Interactor<Void, Word> {

    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;
    @Inject PreferencesRepository preferencesRepository;

    @Inject
    public SaveInHistoryInteractor() {}

    @Override protected Observable<Void> buildObservable(Word param) {
        return historyAndFavouritesRepository
                .saveInHistory(param)
                .mergeWith(preferencesRepository
                        .saveLastWord(param))
                .toObservable();
    }
}
