package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ToggleFavouriteStateInteractor extends Interactor<Void, Void> {

    @Inject PreferencesRepository preferencesRepository;
    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;
    @Inject OnFavouritesChangedInteractor onFavouritesChangedInteractor;
    @Inject RemoveFromFavouritesInteractor removeFromFavouritesInteractor;
    @Inject SaveInFavouriteInteractor saveInFavouriteInteractor;

    @Inject
    public ToggleFavouriteStateInteractor() {
    }

    @Override public Observable<Void> buildObservable(Void param) {
        return preferencesRepository.getLastWord()
                .firstOrError()
                .flatMap(word -> historyAndFavouritesRepository.checkIfInFavourites(word))
                .toObservable()
                .flatMap(word -> {
                    if (word.isFavourite()){
                        return removeFromFavouritesInteractor.execute(word);
                    }else {
                        return saveInFavouriteInteractor.execute(word);
                    }
                });
    }
}
