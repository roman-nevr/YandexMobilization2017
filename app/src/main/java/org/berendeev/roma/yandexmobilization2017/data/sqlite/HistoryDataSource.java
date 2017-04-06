package org.berendeev.roma.yandexmobilization2017.data.sqlite;

import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface HistoryDataSource {
    Observable<List<Word>> getHistory();

    Observable<List<Word>> getFavourites();

    boolean checkIfInFavourites(Word word);

    Completable saveInHistory(Word word);

    Completable saveInFavourites(Word word);

    Completable removeFromHistory(Word word);

    Completable removeFromFavourites(Word word);

    Completable removeAllFromHistory();

    Completable removeAllFromFavourites();

    Observable<Integer> getOnChangeObservable();

    Word getWord(TranslationQuery query);
}
