package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class TranslateTextInteractor extends Interactor<Void, String> {

    @Inject ResultRepository resultRepository;

    @Inject
    public TranslateTextInteractor() {
    }

    //сохраняем запрос и инвалидируем результат
    //т.е. отправляем запрос на перевод
    @Override public Observable<Void> buildObservable(String param) {
        return resultRepository
                .getQueryObservable()
                .firstElement()
                .map(query -> query.toBuilder().text(param).build())
                .flatMapCompletable(query -> resultRepository.saveLastQuery(query))
                .andThen(resultRepository.invalidateResult())
                .toObservable();
    }


}
