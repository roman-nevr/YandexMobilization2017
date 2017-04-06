package org.berendeev.roma.yandexmobilization2017.domain;

import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface HistoryAndFavouritesRepository {
    Observable<List<Word>> getHistory();

    Observable<List<Word>> getFavourites();

    Observable<Word> checkIfInFavourites(Word word);

    Completable saveInHistory(Word word);

    Completable saveInFavourites(Word word);

    Completable removeFromHistory(Word word);

    Completable removeAllFromHistory();

    Completable removeFromFavourites(Word word);

    Completable removeAllFromFavourites();

    Observable<Integer> getOnChangeObservable();

    Maybe<Word> getWord(TranslationQuery query);
}
