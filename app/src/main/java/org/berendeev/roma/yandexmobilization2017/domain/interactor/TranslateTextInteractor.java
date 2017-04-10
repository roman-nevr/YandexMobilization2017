package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import android.util.Pair;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

public class TranslateTextInteractor extends Interactor<Word, String> {

    @Inject TranslationRepository translationRepository;
    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;
    @Inject PreferencesRepository preferencesRepository;

    @Inject
    public TranslateTextInteractor() {
    }

    @Override public Observable<Word> buildObservable(String param) {
        return preferencesRepository
                .saveLastWord(Word.EMPTY.toBuilder()
                        .word(param)
                        .build())
                .andThen(preferencesRepository
                        .getTranslateDirection()
                        .map(dirs -> TranslationQuery.create(param, dirs.first, dirs.second))
                        .flatMap(query -> Observable.concat(
                                historyAndFavouritesRepository
                                        .getWord(query)
                                        .toObservable(),
                                translationRepository
                                        .translate(query)
                                        .toObservable()
                        ))
                        .firstElement()
                        .flatMapCompletable(word ->
                                preferencesRepository.saveLastWord(word)
                        ).toObservable());
    }
}
