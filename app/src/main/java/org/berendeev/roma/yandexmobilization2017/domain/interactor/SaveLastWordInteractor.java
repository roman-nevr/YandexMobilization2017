package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SaveLastWordInteractor extends Interactor<Void, Word> {

    @Inject ResultRepository repository;

    @Inject
    public SaveLastWordInteractor() {}

    @Override public Observable<Void> buildObservable(Word param) {
        return repository.saveResult(param).toObservable();
    }
}
