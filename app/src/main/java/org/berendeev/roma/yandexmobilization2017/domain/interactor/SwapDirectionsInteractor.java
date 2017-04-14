package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

import static org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState.ok;
import static org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState.requested;

public class SwapDirectionsInteractor extends Interactor<Void, Void> {

    @Inject ResultRepository resultRepository;
    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;

    @Inject
    public SwapDirectionsInteractor() {
    }

    @Override public Observable<Void> buildObservable(Void param) {


        return resultRepository
                .getResultObservable()
                .firstElement()
                .toObservable()
                .map(word -> swapMap(word))
                .flatMap(word -> Observable.merge(
                        historyAndFavouritesRepository
                                .saveInHistory(word)
                                .toObservable(),
                        resultRepository
                                .swapDirections()
                                .toObservable(),
                        resultRepository
                                .saveResult(word.toBuilder().translationState(requested).build())
                                .toObservable()));
    }

    private Word swapMap(Word word) {
        if (word.translationState() == ok) {
            return word.toBuilder()
                    .translation(word.word())
                    .word(word.translation())
                    .languageFrom(word.languageTo())
                    .languageTo(word.languageFrom())
                    .isFavourite(false)
                    .build();
        } else {
            return word.toBuilder()
                    .languageFrom(word.languageTo())
                    .languageTo(word.languageFrom())
                    .isFavourite(false)
                    .build();
        }
    }
}
