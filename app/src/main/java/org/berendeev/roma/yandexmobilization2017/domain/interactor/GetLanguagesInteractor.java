package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetLanguagesInteractor extends Interactor<LanguageMap, Locale> {

    @Inject TranslationRepository repository;

    @Inject
    public GetLanguagesInteractor() {}

    @Override public Observable<LanguageMap> buildObservable(Locale param) {
        return repository.getLanguages(param);
    }
}
