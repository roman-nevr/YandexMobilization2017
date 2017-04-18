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
                //если последний результат ok, то сохраняем в историю
                .flatMap(word -> {
                    if (word.translationState() == ok) {
                        return historyAndFavouritesRepository
                                .saveInHistory(word)
                                .andThen(Observable.just(word));
                    } else {
                        return Observable.just(word);
                    }
                })
                //получаем запрос
                .flatMap(word -> resultRepository
                        .getQueryObservable()
                        .firstElement()
                        .map(query -> buildQuery(word, query))
                        .toObservable())
                //сохраняем запрос и инвалидируем результат
                .flatMap(query -> resultRepository
                        .saveLastQuery(query)
                        .andThen(resultRepository
                                .invalidateResult())
                        .toObservable());
    }

    private TranslationQuery buildQuery(Word word, TranslationQuery query) {
        if (word.translationState() == ok) {
            return query.toBuilder()
                    .text(word.translation())
                    .langTo(query.langFrom())
                    .langFrom(query.langTo())
                    .build();
        } else {
            return query.toBuilder()
                    .langTo(query.langFrom())
                    .langFrom(query.langTo())
                    .build();
        }
    }


}
