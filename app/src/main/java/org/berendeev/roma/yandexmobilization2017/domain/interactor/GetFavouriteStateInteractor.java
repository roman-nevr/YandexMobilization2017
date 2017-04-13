package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState;

import javax.inject.Inject;

import io.reactivex.Observable;

import static org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState.ok;

public class GetFavouriteStateInteractor extends Interactor<Boolean, Void> {

    @Inject PreferencesRepository preferencesRepository;
    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;
    @Inject OnFavouritesChangedInteractor onFavouritesChangedInteractor;

    @Inject
    public GetFavouriteStateInteractor() {
    }

    @Override public Observable<Boolean> buildObservable(Void param) {
        return Observable.combineLatest(
                preferencesRepository
                        .getLastWord()
                        .filter(word -> word.translationState() == ok),
                onFavouritesChangedInteractor
                        .execute(null), (word, event) -> word)
                .flatMap(word -> historyAndFavouritesRepository
                        .checkIfInFavourites(word).toObservable())
                .map(word -> word.isFavourite());
    }
}
