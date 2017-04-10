package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class SetWordInTranslatorInteractor extends Interactor<Void, Word> {

    @Inject PreferencesRepository preferencesRepository;
    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;

    @Inject
    public SetWordInTranslatorInteractor() {
    }

    @Override public Observable<Void> buildObservable(Word param) {
        return preferencesRepository
                .saveLastWord(param)
                .andThen(preferencesRepository
                        .setDirectionFrom(param.languageFrom()))
                .andThen(preferencesRepository
                        .setDirectionTo(param.languageTo()))
                .andThen(historyAndFavouritesRepository
                        .saveInHistory(param))
                .toObservable();
    }
}
