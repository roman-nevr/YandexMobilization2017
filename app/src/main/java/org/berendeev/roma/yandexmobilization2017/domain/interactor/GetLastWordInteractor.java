package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetLastWordInteractor extends Interactor<Word, Void> {

    @Inject PreferencesRepository repository;
    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;
    @Inject OnFavouritesChangedInteractor onFavouritesChangedInteractor;

    @Inject
    public GetLastWordInteractor() {
    }

    @Override public Observable<Word> buildObservable(Void param) {
        Observable<Word> observable = repository
                .getLastWord()
                .distinctUntilChanged()
                .flatMap(word -> historyAndFavouritesRepository
                        .checkIfInFavourites(word)
                        .toObservable());
        Observable<Word> wordObservable = onFavouritesChangedInteractor
                .execute(null)
                .flatMap(integer -> observable);
        return Observable.merge(observable, wordObservable);
    }
}
