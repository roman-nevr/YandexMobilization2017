package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ToggleFavouriteStateInteractor extends Interactor<Boolean, Void> {

    @Inject ResultRepository resultRepository;
    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;
    @Inject OnFavouritesChangedInteractor onFavouritesChangedInteractor;
    @Inject RemoveFromFavouritesInteractor removeFromFavouritesInteractor;
    @Inject SaveInFavouriteInteractor saveInFavouriteInteractor;

    @Inject
    public ToggleFavouriteStateInteractor() {
    }

    @Override public Observable<Boolean> buildObservable(Void param) {
        return resultRepository.getResultObservable()
                .firstOrError()
                .flatMap(word -> historyAndFavouritesRepository.checkIfInFavourites(word))
                .toObservable()
                .flatMap(word -> {
                    if (word.isFavourite()){
                        return historyAndFavouritesRepository
                                .removeFromFavourites(word)
                                .andThen(Observable.just(false));
                    }else {
                        return historyAndFavouritesRepository
                                .saveInFavourites(word)
                                .andThen(Observable.just(true));
                    }
                });
    }
}
