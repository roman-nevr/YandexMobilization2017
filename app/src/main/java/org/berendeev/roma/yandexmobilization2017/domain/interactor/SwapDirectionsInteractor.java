package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

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
                .flatMap(word -> Observable.concat(
                        resultRepository
                                .saveLastQuery(buildQuery(word))
                                .toObservable(),
                        resultRepository
                                .invalidateResult()
                                .toObservable()));
    }

    private TranslationQuery buildQuery(Word word) {
        return TranslationQuery.create(word.translation(), word.languageTo(), word.languageFrom());
    }


}
