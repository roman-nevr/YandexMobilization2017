package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SetWordInTranslatorInteractor extends Interactor<Void, Word> {

    @Inject ResultRepository resultRepository;
    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;

    @Inject
    public SetWordInTranslatorInteractor() {
    }

    @Override public Observable<Void> buildObservable(Word word) {
        return resultRepository
                .saveLastQuery(word.word())
                .andThen(resultRepository
                        .setDirectionFrom(word.languageFrom()))
                .andThen(resultRepository
                        .setDirectionTo(word.languageTo()))
                .andThen(resultRepository
                        .saveResult(word))
                .andThen(historyAndFavouritesRepository
                        .saveInHistory(word))
                .toObservable();
    }
}
