package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import android.util.Pair;

import org.berendeev.roma.yandexmobilization2017.data.mapper.TranslateMapper;
import org.berendeev.roma.yandexmobilization2017.domain.DictionaryRepository;
import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

import static org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState.requested;

public class TranslateTextInteractor extends Interactor<Void, String> {

    @Inject TranslationRepository translationRepository;
    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;
    @Inject PreferencesRepository preferencesRepository;
    @Inject DictionaryRepository dictionaryRepository;

    @Inject
    public TranslateTextInteractor() {
    }

    @Override public Observable<Void> buildObservable(String param) {
        return preferencesRepository
                .getTranslateDirection()
                .map(dirs -> TranslationQuery.create(param, dirs.first, dirs.second))
                .flatMap(query -> preferencesRepository
                        .getLastWord()
                        .firstElement()
                        .map(word -> word.toBuilder()
                                .word(param)
                                .languageFrom(query.langFrom())
                                .languageTo(query.langTo())
                                .translationState(requested)
                                .build())
                        .flatMapCompletable(word -> preferencesRepository
                                .saveLastWord(word))
                        .toObservable());
    }


}
