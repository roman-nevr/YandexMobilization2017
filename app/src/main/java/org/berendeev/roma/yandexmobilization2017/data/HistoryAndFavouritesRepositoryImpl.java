package org.berendeev.roma.yandexmobilization2017.data;

import org.berendeev.roma.yandexmobilization2017.data.sqlite.HistoryDataSource;
import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


public class HistoryAndFavouritesRepositoryImpl implements HistoryAndFavouritesRepository {

    private HistoryDataSource historyDataSource;

    public HistoryAndFavouritesRepositoryImpl(HistoryDataSource historyDataSource) {
        this.historyDataSource = historyDataSource;
    }

    @Override public Observable<List<Word>> getHistory() {
        return historyDataSource.getHistory();
    }

    @Override public Observable<List<Word>> getFavourites() {
        return historyDataSource.getFavourites();
    }

    @Override public Observable<Word> checkIfInFavourites(Word word) {
        if(historyDataSource.checkIfInFavourites(word)){
            return Observable
                    .just(word
                    .toBuilder()
                    .isFavourite(true)
                    .build());
        }else {
            return Observable.just(word);
        }
    }

    @Override public Completable saveInHistory(Word word) {
        return historyDataSource.saveInHistory(word);
    }

    @Override public Completable saveInFavourites(Word word) {
        return historyDataSource.saveInFavourites(word);
    }

    @Override public Completable removeFromHistory(Word word) {
        return historyDataSource.removeFromHistory(word);
    }

    @Override public Completable removeFromFavourites(Word word) {
        return historyDataSource.removeFromFavourites(word);
    }
}
