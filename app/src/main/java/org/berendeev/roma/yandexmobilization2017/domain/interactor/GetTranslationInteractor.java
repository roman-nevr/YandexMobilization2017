package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.data.mapper.TranslateMapper;
import org.berendeev.roma.yandexmobilization2017.domain.DictionaryRepository;
import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.domain.exception.TranslationException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

import static org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState.connectionError;
import static org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState.requested;
import static org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState.translationError;

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
                .flatMap(word -> historyAndFavouritesRepository
                        .checkIfInFavourites(word)
                        .toObservable())
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
                                            .toObservable()
                                            .flatMap(receivedWord ->
                                                    preferencesRepository
                                                            .saveLastWord(receivedWord)
                                                            .andThen(historyAndFavouritesRepository
                                                                    .checkIfInFavourites(receivedWord)
                                                                    .toObservable()));
                                })
                                .onErrorResumeNext(throwable -> {
                                    if (throwable instanceof TranslationException) {
                                        return Observable.just(lastWord.toBuilder().translationState(translationError).build());
                                    }
                                    return Observable.just(lastWord.toBuilder().translationState(connectionError).build());
                                });
                    } else {
                        return Observable.just(lastWord);
                    }
                })
                .filter(word -> word.translationState() != requested)
                .distinctUntilChanged();
    }
}
