package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class TranslateTextInteractor extends Interactor<Void, String> {

    @Inject ResultRepository resultRepository;

    @Inject
    public TranslateTextInteractor() {
    }

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
