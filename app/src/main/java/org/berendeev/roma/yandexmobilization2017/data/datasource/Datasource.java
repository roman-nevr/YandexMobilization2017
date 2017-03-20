package org.berendeev.roma.yandexmobilization2017.data.datasource;

import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

import io.reactivex.Observable;

public interface Datasource {
    Observable<Word> getTranslation(Word word);
    Observable<List<String>> getLanguages();
}
