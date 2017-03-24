package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SaveLastWordInteractor extends Interactor<Void, Word> {

    @Inject PreferencesRepository repository;

    @Inject
    public SaveLastWordInteractor() {}

    @Override protected Observable<Void> buildObservable(Word param) {
        return repository.saveLastWord(param).toObservable();
    }
}
