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

    Completable saveLastQuery(TranslationQuery query);

    Completable invalidateResult();

    Observable<TranslationQuery> getQueryObservable();

    Completable setDirectionTo(String to);

    Completable setDirectionFrom(String from);
}
