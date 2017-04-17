package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RepeatInteractor extends Interactor<List<Word>, Void> {

    @Inject ResultRepository repository;

    @Inject
    public RepeatInteractor() {}

    @Override public Observable<List<Word>> buildObservable(Void param) {
        return repository
                .invalidateResult()
                .toObservable();
    }
}
