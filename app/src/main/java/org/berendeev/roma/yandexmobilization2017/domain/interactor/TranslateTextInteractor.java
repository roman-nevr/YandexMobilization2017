package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

public class TranslateTextInteractor extends Interactor<Word, TranslationQuery> {

    @Inject TranslationRepository repository;

    @Inject
    public TranslateTextInteractor() {}

    @Override protected Observable<Word> buildObservable(TranslationQuery param) {
        return repository.translate(param);
    }
}
