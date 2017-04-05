package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.DictionaryRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetDictionaryInteractor extends Interactor<Dictionary, TranslationQuery> {

    @Inject DictionaryRepository repository;

    @Inject
    public GetDictionaryInteractor() {}

    @Override public Observable<Dictionary> buildObservable(TranslationQuery param) {
        return repository.lookup(param);
    }
}
