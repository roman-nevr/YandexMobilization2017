package org.berendeev.roma.yandexmobilization2017.data.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper.IS_IN_FAVOURITES;
import static org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper.IS_IN_HISTORY;
import static org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper.LANGUAGE_FROM;
import static org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper.LANGUAGE_TO;
import static org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper.TRANSLATION;
import static org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper.WORD;
import static org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper.WORDS_TABLE;

public class DatabaseHistoryDataSource implements HistoryDataSource {

    private SQLiteDatabase database;
    private final ContentValues contentValues;

    public DatabaseHistoryDataSource(DatabaseOpenHelper openHelper) {
        this.database = openHelper.getWritableDatabase();
        contentValues = new ContentValues();
    }

    @Override public Observable<List<Word>> getHistory() {
        String selection = IS_IN_HISTORY + " = ?";
        return getFromSelection(selection);
    }

    @Override public Observable<List<Word>> getFavourites() {
        String selection = IS_IN_FAVOURITES + " = ?";
        return getFromSelection(selection);
    }

    @Override public boolean checkIfInFavourites(Word word) {
        String selection = String.format("%1s = ? AND %2s = ? AND %3s = ? AND %4s = ?", WORD, TRANSLATION, LANGUAGE_FROM, LANGUAGE_TO);
        String[] selectionArgs = {word.word(), word.translation(), word.languageFrom(), word.languageTo()};
        Cursor cursor = database.query(WORDS_TABLE, null, selection, selectionArgs, null, null, null, null);
        if (cursor.moveToFirst()){
            cursor.close();
            return true;
        }else {
            cursor.close();
            return false;
        }
    }

    private Observable<List<Word>> getFromSelection(String selection){
        return Observable.create(emitter -> {
            List<Word> words = new ArrayList<>();
            String[] selectionArgs = {"1"};
            Cursor cursor = database.query(WORDS_TABLE, null, selection, selectionArgs, null, null, null, null);
            while (cursor.moveToNext()){
                words.add(getWordFromCursor(cursor));
            }
            cursor.close();
            if(!emitter.isDisposed()){
                emitter.onNext(words);
                emitter.onComplete();
            }
        });
    }

    private Word getWordFromCursor(Cursor cursor) {
        int wordIndex = cursor.getColumnIndex(WORD);
        int translationIndex = cursor.getColumnIndex(TRANSLATION);
        int fromIndex = cursor.getColumnIndex(LANGUAGE_FROM);
        int toIndex = cursor.getColumnIndex(LANGUAGE_TO);
        int isFavouriteIndex = cursor.getColumnIndex(IS_IN_FAVOURITES);
        return Word.builder()
                .word(cursor.getString(wordIndex))
                .translation(cursor.getString(translationIndex))
                .languageFrom(cursor.getString(fromIndex))
                .languageTo(cursor.getString(toIndex))
                .isFavourite(getBooleanFromSQLInteger(cursor.getInt(isFavouriteIndex)))
                .build();
    }

    private boolean getBooleanFromSQLInteger(int anInt) {
        return anInt == 1;
    }

    private int getSQLIntegerFromBoolean(boolean bool){
        return bool ? 1:0;
    }
}
