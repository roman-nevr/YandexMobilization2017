package org.berendeev.roma.yandexmobilization2017.domain;

import android.util.Pair;

import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface ResultRepository {
    Completable saveResult(Word word);

    Observable<Word> getResultObservable();

    Completable saveLastQuery(String query);

    Completable invalidateResult();

    Observable<String> getQueryObservable();

    Observable<Pair<String, String>> getTranslateDirection();

    Completable setDirectionTo(String to);

    Completable setDirectionFrom(String from);

    Completable swapDirections();
}
