package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetLastWordInteractor extends Interactor<Word, Void> {

    @Inject PreferencesRepository repository;
    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;

    @Inject
    public GetLastWordInteractor() {}

    @Override public Observable<Word> buildObservable(Void param) {
        return repository.getLastWord().flatMapObservable(word ->
            historyAndFavouritesRepository.checkIfInFavourites(word)
        );
    }
}
