package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetLastWordInteractor extends Interactor<Word, Void> {

    @Inject PreferencesRepository repository;

    @Inject
    public GetLastWordInteractor() {}

    @Override protected Observable<Word> buildObservable(Void param) {
        return repository.getLastWord().toObservable();
    }
}
