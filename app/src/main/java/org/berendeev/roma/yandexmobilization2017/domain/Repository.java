package org.berendeev.roma.yandexmobilization2017.domain;

import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

import io.reactivex.Observable;

public interface Repository {
    Observable<Word> translate(Word word);
    Observable<List<String>> getLanguages();
}
