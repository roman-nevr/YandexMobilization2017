package org.berendeev.roma.yandexmobilization2017.data.datasource;


import android.util.Pair;

import com.google.gson.Gson;

import org.berendeev.roma.yandexmobilization2017.data.Cache;
import org.berendeev.roma.yandexmobilization2017.data.DataSource;
import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;

public class MemoryCacheDataSource implements DataSource, Cache {

    private Gson gson;
    private List<Word> queries;
    private List<String> cache;
    private List<LanguageMap> languageMapList;
    private Locale locale;
    private Word lastWord;

    public MemoryCacheDataSource(Word lastWord, Gson gson) {
        this.lastWord = lastWord;
        this.gson = gson;
    }

    @Override public void persist(Word query, String json) {
        int index = queries.indexOf(query);
        if(index != -1){
            queries.add(query);
            cache.add(json);
        }
        lastWord = query.toBuilder().translation()
    }

    @Override public void persist(Locale locale, String json) {

    }

    @Override public Observable<String> getTranslation(Word word) {
        return null;
    }

    @Override public Observable<String> getLanguages(Locale locale) {
        return null;
    }

    @Override public Observable<Word> getLastWord() {
        return null;
    }
}
