package org.berendeev.roma.yandexmobilization2017.data.sqlite;

import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

import io.reactivex.Observable;

public interface HistoryDataSource {
    Observable<List<Word>> getHistory();

    Observable<List<Word>> getFavourites();

    boolean checkIfInFavourites(Word word);
}
