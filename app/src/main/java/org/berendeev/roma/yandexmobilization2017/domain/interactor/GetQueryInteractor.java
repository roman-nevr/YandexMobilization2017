package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetQueryInteractor extends Interactor<String, Void> {

    @Inject ResultRepository repository;

    @Inject
    public GetQueryInteractor() {}

    @Override public Observable<String> buildObservable(Void param) {
        return repository.getQueryObservable().map(query -> query.text());
    }
}
