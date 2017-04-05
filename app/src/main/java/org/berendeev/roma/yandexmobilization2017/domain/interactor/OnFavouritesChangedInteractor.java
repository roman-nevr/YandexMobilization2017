package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class OnFavouritesChangedInteractor extends Interactor<Integer, Void> {

    @Inject HistoryAndFavouritesRepository repository;

    @Inject
    public OnFavouritesChangedInteractor() {}

    @Override public Observable<Integer> buildObservable(Void param) {
        return repository.getOnChangeObservable()
                .filter(integer -> integer == R.id.favourites_type);
    }
}
