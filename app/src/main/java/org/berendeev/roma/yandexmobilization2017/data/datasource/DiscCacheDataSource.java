package org.berendeev.roma.yandexmobilization2017.data.datasource;

import android.content.Context;

import org.berendeev.roma.yandexmobilization2017.data.Cache;
import org.berendeev.roma.yandexmobilization2017.data.DataSource;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.Locale;

import io.reactivex.Observable;


public class DiscCacheDataSource implements DataSource, Cache {

    private Context context;

    public DiscCacheDataSource(Context context) {
        this.context = context;
    }

    @Override public void persist(Word word, String json) {

    }

    @Override public void persist(Locale locale, String json) {

    }

    @Override public Observable<String> getTranslation(Word word) {
        return null;
    }

    @Override public Observable<String> getLanguages(Locale locale) {
        return null;
    }
}
