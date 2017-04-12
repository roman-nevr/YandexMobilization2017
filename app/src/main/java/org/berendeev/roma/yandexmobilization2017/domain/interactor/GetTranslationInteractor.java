package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.data.entity.Translation;
import org.berendeev.roma.yandexmobilization2017.data.mapper.TranslateMapper;
import org.berendeev.roma.yandexmobilization2017.domain.DictionaryRepository;
import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

import static org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState.error;
import static org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState.ok;
import static org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState.requested;

public class GetTranslationInteractor extends Interactor<Word, Void> {

    @Inject TranslationRepository translationRepository;
    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;
    @Inject PreferencesRepository preferencesRepository;
    @Inject DictionaryRepository dictionaryRepository;

    @Inject
    public GetTranslationInteractor() {
    }

    @Override public Observable<Word> buildObservable(Void param) {
        return preferencesRepository
                .getLastWord()
                .flatMap(lastWord -> {
                    if (lastWord.word().equals("")) {
                        return Observable.just(Word.EMPTY);
                    }
                    if (lastWord.translationState() == requested) {
                        return Observable.just(lastWord)
                                .flatMap(word -> {
                                    TranslationQuery query = TranslationQuery.create(word.word(), word.languageFrom(), word.languageTo());
                                    return Single.zip(
                                            translationRepository
                                                    .translate(query),
                                            dictionaryRepository
                                                    .lookup(query),
                                            (translation, dictionary) ->
                                                    TranslateMapper.map(query, translation, dictionary))
                                            .toObservable();
                                })
                                .onErrorResumeNext(throwable -> {
                                    return Observable.just(lastWord.toBuilder().translationState(error).build());
                                })
                                .flatMap(word -> preferencesRepository
                                        .saveLastWord(word).toObservable());
//                        return translationRepository.translate(TranslationQuery.create(word.word(), word.languageFrom(), word.languageTo()))
//                                .map(translation -> word.toBuilder().translation(translation.translation.get(0)).translationState(ok).build())
//                                .flatMapObservable(word1 -> preferencesRepository
//                                        .saveLastWord(word1).toObservable());
                    } else {
                        return Observable.just(lastWord);
                    }
                })
                .filter(word -> word.translationState() != requested)
                .distinctUntilChanged();
    }
}
