package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RemoveAllFromFavouritesInteractor extends Interactor<Void, Void> {

    @Inject HistoryAndFavouritesRepository repository;

    @Inject
    public RemoveAllFromFavouritesInteractor() {}

    @Override public Observable<Void> buildObservable(Void param) {
        return repository.removeAllFromFavourites()
                .toObservable();
    }

}
