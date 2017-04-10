package org.berendeev.roma.yandexmobilization2017.domain;

import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface DictionaryRepository {
    Single<Dictionary> lookup(TranslationQuery query);
}
