package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ToggleIsFavouriteInLastWordInteractor extends Interactor<Void, Word> {
    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;
    @Inject PreferencesRepository preferencesRepository;

    @Inject
    public ToggleIsFavouriteInLastWordInteractor() {
    }

    @Override public Observable<Void> buildObservable(Word param) {
        Observable<Word> cache = preferencesRepository
                .getLastWord()
                .cache();
        return Observable.merge(
                cache.flatMap(word -> {
                    if (word.isFavourite()) {
                        return historyAndFavouritesRepository
                                .removeFromFavourites(word)
                                .toObservable();
                    } else {
                        return historyAndFavouritesRepository
                                .saveInFavourites(word)
                                .toObservable();
                    }
                }),
                cache.flatMap(word ->
                        preferencesRepository
                                .saveLastWord(word.toBuilder()
                                        .isFavourite(!word.isFavourite())
                                        .build())
                                .toObservable()
                ));
    }
}
