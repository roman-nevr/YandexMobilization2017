package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetFavouritesInteractor extends Interactor<List<Word>, Void> {

    @Inject HistoryAndFavouritesRepository repository;

    @Inject
    public GetFavouritesInteractor() {}

    @Override public Observable<List<Word>> buildObservable(Void param) {
        return repository.getFavourites();
    }
}
