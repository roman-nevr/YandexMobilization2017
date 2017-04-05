package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetHistoryInteractor extends Interactor<List<Word>, Void> {

    @Inject HistoryAndFavouritesRepository repository;

    @Inject
    public GetHistoryInteractor() {}

    @Override public Observable<List<Word>> buildObservable(Void param) {
        return repository.getHistory();
    }
}
