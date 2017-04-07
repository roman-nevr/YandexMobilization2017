package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SaveLastWordInHistoryInteractor extends Interactor<Void, Void> {

    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;
    @Inject PreferencesRepository preferencesRepository;

    @Inject
    public SaveLastWordInHistoryInteractor() {}

    @Override public Observable<Void> buildObservable(Void param) {
        return preferencesRepository
                .getLastWord()
                .flatMap(word ->
                    historyAndFavouritesRepository
                            .saveInHistory(word)
                            .toObservable());
    }
}
