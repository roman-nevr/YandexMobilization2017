package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class RemoveAllFromFavouritesInteractor extends Interactor<List<Word>, Void> {

    @Inject HistoryAndFavouritesRepository repository;

    @Inject
    public RemoveAllFromFavouritesInteractor() {}

    @Override protected Observable<List<Word>> buildObservable(Void param) {
        return repository.removeAllFromFavourites()
                .andThen(repository.getFavourites());
    }

}
