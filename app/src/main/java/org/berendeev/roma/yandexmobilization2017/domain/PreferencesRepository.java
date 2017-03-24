package org.berendeev.roma.yandexmobilization2017.domain;

import android.util.Pair;

import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface PreferencesRepository {
    Completable saveLastWord(Word word);

    Single<Word> getLastWord();

    Observable<Pair<String, String>> getTranslateDirection();

    Completable setDirectionTo(String to);

    Completable setDirectionFrom(String from);

    Completable swapDirections();
}
