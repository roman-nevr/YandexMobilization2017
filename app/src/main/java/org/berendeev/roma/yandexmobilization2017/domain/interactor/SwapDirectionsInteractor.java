package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

import static org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState.ok;

public class SwapDirectionsInteractor extends Interactor<Void, Void> {

    @Inject ResultRepository resultRepository;
    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;

    @Inject
    public SwapDirectionsInteractor() {
    }

    //меняем местами языки ввода и перевода
    @Override public Observable<Void> buildObservable(Void param) {
        return resultRepository
                .getResultObservable()
                .firstElement()
                .toObservable()
                .flatMap(word -> {
                    if(word.translationState() == ok){
                        return historyAndFavouritesRepository
                                .saveInHistory(word)
                                .andThen(Observable.just(word));
                    }else {
                        return Observable.just(word);
                    }
                })
                .flatMap(word -> Observable.concat(
                        resultRepository
                                .saveLastQuery(buildQuery(word))
                                .toObservable(),
                        resultRepository
                                .invalidateResult()
                                .toObservable()));
    }

    private TranslationQuery buildQuery(Word word) {
        String query;
        if (word.translationState() == ok){
            query = word.translation();
        }else {
            query = word.word();
        }
        return TranslationQuery.create(query, word.languageTo(), word.languageFrom());
    }


}
