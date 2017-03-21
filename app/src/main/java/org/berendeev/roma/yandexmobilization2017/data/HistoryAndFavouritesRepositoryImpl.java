package org.berendeev.roma.yandexmobilization2017.data;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

import io.reactivex.Observable;


public class HistoryAndFavouritesRepositoryImpl implements HistoryAndFavouritesRepository {
    @Override public Observable<List<Word>> getHistory() {
        return null;
    }

    @Override public Observable<List<Word>> getFavourites() {
        return null;
    }

    @Override public Observable<Word> checkIfInFavourites(Word word) {
        return null;
    }
}
