package org.berendeev.roma.yandexmobilization2017.data.datasource;


import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

import io.reactivex.Observable;

public class HttpDatasource implements Datasource {



    @Override public Observable<Word> translate(Word word) {
        return null;
    }

    @Override public Observable<List<String>> getLanguages() {
        return null;
    }
}
