package org.berendeev.roma.yandexmobilization2017.data.datasource;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CacheAndHistoryImpl implements Cache, History {

    private final DatabaseOpenHelper openHelper;
    private final SQLiteDatabase database;
    private final ContentValues contentValues;

    public CacheAndHistoryImpl(DatabaseOpenHelper openHelper) {
        this.openHelper = openHelper;
        database = openHelper.getWritableDatabase();
        contentValues = new ContentValues();
    }

    @Override public void invalidateCache() {
    }

    @Override public Observable<Word> translate(Word word) {
        return Observable.empty();
    }

    @Override public Observable<List<String>> getLanguages() {
        return Observable.empty();
    }

    @Override public Observable<List<Word>> getHistory() {
        return null;
    }

    @Override public Observable<List<Word>> getFavourite() {
        return null;
    }
}
