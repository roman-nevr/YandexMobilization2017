package org.berendeev.roma.yandexmobilization2017.domain;

import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

import io.reactivex.Observable;

public interface HistoryAndFavouritesRepository {
    Observable<List<Word>> getHistory();

    Observable<List<Word>> getFavourites();

    Observable<Word> checkIfInFavourites(Word word);
}
