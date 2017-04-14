package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.DictionaryRepository;
import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetDictionaryInteractor extends Interactor<Dictionary, String> {

    @Inject DictionaryRepository repository;
    @Inject ResultRepository resultRepository;

    @Inject
    public GetDictionaryInteractor() {
    }

    @Override public Observable<Dictionary> buildObservable(String param) {
        return resultRepository
                .getTranslateDirection()
                .firstElement()
                .map(stringStringPair -> TranslationQuery.create(param, stringStringPair.first, stringStringPair.second))
                .flatMapSingle(query -> repository
                        .lookup(query))
                .toObservable();
    }
}
