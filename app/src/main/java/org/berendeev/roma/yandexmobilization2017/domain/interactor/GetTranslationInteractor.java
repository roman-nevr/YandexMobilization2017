package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.data.mapper.TranslateMapper;
import org.berendeev.roma.yandexmobilization2017.domain.DictionaryRepository;
import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;
import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.domain.exception.ConnectionException;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

import static org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState.connectionError;
import static org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState.ok;
import static org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState.requested;

public class GetTranslationInteractor extends Interactor<Word, Void> {

    @Inject TranslationRepository translationRepository;
    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;
    @Inject ResultRepository resultRepository;
    @Inject DictionaryRepository dictionaryRepository;

    @Inject
    public GetTranslationInteractor() {
    }

    @Override public Observable<Word> buildObservable(Void param) {
        return Observable.merge(
                onQueryChangedObservable(),
                onValidResultObservable())
                .distinctUntilChanged();
    }

    private Observable<Word> onQueryChangedObservable() {
        return getQueryObservable()
                .flatMap(query -> translateWord(query)
                        .flatMap(translatedWord -> historyAndFavouritesRepository
                                .checkIfInFavourites(translatedWord)
                                .toObservable())
                        .onErrorResumeNext(throwable -> {
                            return getOnErrorObservable(throwable, query);
                        })
                        .flatMap(receivedWord ->
                                resultRepository
                                        .saveResult(receivedWord)
                                        .toObservable())
                );
    }

    private Observable<Word> onValidResultObservable() {
        return resultRepository
                .getResultObservable()
                .filter(word -> word.translationState() == ok || word.translationState() == connectionError)
                .flatMap(word -> historyAndFavouritesRepository
                        .checkIfInFavourites(word)
                        .toObservable());
    }

    private Observable<Word> translateWord(TranslationQuery query) {
        return Observable.just(query)
                .flatMap(query1 -> Single.zip(
                        translationRepository
                                .translate(query),
                        dictionaryRepository
                                .lookup(query),
                        (translation, dictionary) ->
                                TranslateMapper.map(query, translation, dictionary))
                        .toObservable());
    }

    private Observable<Word> getOnErrorObservable(Throwable throwable, TranslationQuery query) {
        if (throwable instanceof ConnectionException) {
            return Observable.just(Word.EMPTY.toBuilder()
                    .word(query.text())
                    .languageFrom(query.langFrom())
                    .languageTo(query.langTo())
                    .translationState(connectionError)
                    .build());
        } else {
            return Observable.just(Word.EMPTY);
        }
    }

    private Observable<TranslationQuery> getQueryObservable() {
        return resultRepository
                .getResultObservable()
                .filter(word -> word.translationState() == requested)
                .flatMap(word -> resultRepository.getQueryObservable().firstElement().toObservable());
    }
}
