package org.berendeev.roma.yandexmobilization2017.domain;

import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import io.reactivex.Observable;

public interface DictionaryRepository {
    Observable<Dictionary> lookup(TranslationQuery query);
}
