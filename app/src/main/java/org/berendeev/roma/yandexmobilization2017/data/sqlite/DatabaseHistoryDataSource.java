package org.berendeev.roma.yandexmobilization2017.data.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

import static org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper.ADD_DATE;
import static org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper.IS_IN_FAVOURITES;
import static org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper.IS_IN_HISTORY;
import static org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper.LANGUAGE_FROM;
import static org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper.LANGUAGE_TO;
import static org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper.TRANSLATION;
import static org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper.WORD;
import static org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper.WORDS_TABLE;

public class DatabaseHistoryDataSource implements HistoryDataSource {

    public static final String TRUE = "1";
    public static final String FALSE = "0";
    private SQLiteDatabase database;
    private final ContentValues contentValues;
    private PublishSubject<Integer> publishSubject;

    public DatabaseHistoryDataSource(DatabaseOpenHelper openHelper) {
        this.database = openHelper.getWritableDatabase();
        contentValues = new ContentValues();
    }

    @Override public Observable<List<Word>> getHistory() {
        String selection = IS_IN_HISTORY + " = ?";
        return getFromSelection(selection);
//        return historySubject;
    }

    @Override public Observable<List<Word>> getFavourites() {
        String selection = IS_IN_FAVOURITES + " = ?";
        return getFromSelection(selection);
//        return favouritesSubject;
    }

    @Override public boolean checkIfInFavourites(Word word) {
        String selection = String.format("%1s = ? AND %2s = ? AND %3s = ? AND %4s = ? AND %5s = ?", WORD, TRANSLATION, LANGUAGE_FROM, LANGUAGE_TO, IS_IN_FAVOURITES);
        String[] selectionArgs = {word.word(), word.translation(), word.languageFrom(), word.languageTo(), "" + getSqlBooleanFromJavaBoolean(true)};
        boolean result;
        try {
            Cursor cursor = database.query(WORDS_TABLE, null, selection, selectionArgs, null, null, null, null);
            result = cursor.moveToFirst();
            cursor.close();
        }catch (SQLException e){
            result = false;
            Timber.d("SQL Exception " + e);
            Timber.d(e);
        }
        return result;
    }

    @Override public Completable saveInHistory(Word word) {
        return Completable.fromAction(() -> {
            insertOrUpdate(word, IS_IN_HISTORY);
            publishSubject.onNext(R.id.history_type);
        });
    }

    @Override public Completable saveInFavourites(Word word) {
        return Completable.fromAction(() -> {
            insertOrUpdate(word.toBuilder().isFavourite(true).build(), IS_IN_FAVOURITES);
            publishSubject.onNext(R.id.favourites_type);
        });
    }

    private void insertOrUpdate(Word word, String attr){
        String selection = String.format("%1s = ? AND %2s = ? AND %3s = ? AND %4s = ? AND (%5s = ? OR %6s = ?)",
                WORD, TRANSLATION, LANGUAGE_FROM, LANGUAGE_TO, IS_IN_HISTORY, IS_IN_FAVOURITES);
        String[] selectionArgs = {word.word(), word.translation(), word.languageFrom(), word.languageTo(), TRUE, TRUE};
        contentValues.clear();
        contentValues.put(attr, getSqlBooleanFromJavaBoolean(true));
        int count = database.update(WORDS_TABLE, contentValues, selection, selectionArgs);
        if(count == 0){
            fillContentValuesFromWord(word, IS_IN_HISTORY.equals(attr));
            database.insert(WORDS_TABLE, null, contentValues);
        }
    }

    @Override public Completable removeFromHistory(Word word) {
        return Completable.fromAction(() -> {
            String selection = String.format("%1s = ? AND %2s = ? AND %3s = ? AND %4s = ? AND %5s = ? AND %6s = ?",
                    WORD, TRANSLATION, LANGUAGE_FROM, LANGUAGE_TO, IS_IN_HISTORY, IS_IN_FAVOURITES);
            String[] selectionArgs = {word.word(), word.translation(), word.languageFrom(), word.languageTo(), TRUE, FALSE};
            int count = database.delete(WORDS_TABLE, selection, selectionArgs);
            if(count == 0){
                selectionArgs[5] = TRUE;
                contentValues.clear();
                contentValues.put(IS_IN_HISTORY, getSqlBooleanFromJavaBoolean(false));
                database.update(WORDS_TABLE, contentValues, selection, selectionArgs);
            }
            publishSubject.onNext(R.id.history_type);
        });
    }

    @Override public Completable removeFromFavourites(Word word) {
        return Completable.fromAction(() -> {
            String selection = String.format("%1s = ? AND %2s = ? AND %3s = ? AND %4s = ? AND %5s = ? AND %6s = ?",
                    WORD, TRANSLATION, LANGUAGE_FROM, LANGUAGE_TO, IS_IN_FAVOURITES, IS_IN_HISTORY);
            String[] selectionArgs = {word.word(), word.translation(), word.languageFrom(), word.languageTo(), TRUE, FALSE};
            int count = database.delete(WORDS_TABLE, selection, selectionArgs);
            if(count == 0){
                selectionArgs[5] = TRUE;
                contentValues.clear();
                contentValues.put(IS_IN_FAVOURITES, getSqlBooleanFromJavaBoolean(false));
                database.update(WORDS_TABLE, contentValues, selection, selectionArgs);
            }
            publishSubject.onNext(R.id.favourites_type);
        });
    }

    @Override public Completable removeAllFromHistory() {
        return Completable.fromAction(() -> {
            String selection = String.format("%1s = ? AND %2s = ?", IS_IN_HISTORY, IS_IN_FAVOURITES);
            String[] selectionArgs = {TRUE, FALSE};
            database.delete(WORDS_TABLE, selection, selectionArgs);
            selectionArgs[1] = TRUE;
            contentValues.clear();
            contentValues.put(IS_IN_HISTORY, getSqlBooleanFromJavaBoolean(false));
            database.update(WORDS_TABLE, contentValues, selection, selectionArgs);
            publishSubject.onNext(R.id.history_type);
        });
    }

    @Override public Completable removeAllFromFavourites() {
        return Completable.fromAction(() -> {
            String selection = String.format("%1s = ? AND %2s = ?", IS_IN_FAVOURITES, IS_IN_HISTORY);
            String[] selectionArgs = {TRUE, FALSE};
            database.delete(WORDS_TABLE, selection, selectionArgs);
            selectionArgs[1] = TRUE;
            contentValues.clear();
            contentValues.put(IS_IN_FAVOURITES, getSqlBooleanFromJavaBoolean(false));
            database.update(WORDS_TABLE, contentValues, selection, selectionArgs);
            publishSubject.onNext(R.id.favourites_type);
        });
    }

    @Override public Observable<Integer> getOnChangeObservable() {
        return publishSubject;
    }

    public List<Pair<Word, Boolean>> getAll(){
        List<Pair<Word, Boolean>> words = new ArrayList<>();
        Cursor cursor = database.query(WORDS_TABLE, null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int historyIndex = cursor.getColumnIndex(IS_IN_HISTORY);
            words.add(new Pair<>(getWordFromCursor(cursor), getJavaBooleanFromSqlBoolean(cursor.getInt(historyIndex))));
        }
        cursor.close();
        return words;
    }

    private Observable<List<Word>> getFromSelection(String selection) {
        return Observable.create(emitter -> {
            List<Word> words = new ArrayList<>();
            String[] selectionArgs = {TRUE};
            String orderBy = DatabaseOpenHelper.ADD_DATE + " DESC";
            Cursor cursor = database.query(true, WORDS_TABLE, null, selection, selectionArgs, null, null, orderBy, null);
            while (cursor.moveToNext()) {
                words.add(getWordFromCursor(cursor));
            }
            cursor.close();
            if (!emitter.isDisposed()) {
                emitter.onNext(words);
                emitter.onComplete();
            }
        });
    }

    private Word getWordFromCursor(Cursor cursor) {
        int wordIndex = cursor.getColumnIndex(WORD);
        int translationIndex = cursor.getColumnIndex(TRANSLATION);
        int toIndex = cursor.getColumnIndex(LANGUAGE_TO);
        int fromIndex = cursor.getColumnIndex(LANGUAGE_FROM);
        int isFavouriteIndex = cursor.getColumnIndex(IS_IN_FAVOURITES);
        return Word.builder()
                .word(cursor.getString(wordIndex))
                .translation(cursor.getString(translationIndex))
                .languageFrom(cursor.getString(fromIndex))
                .languageTo(cursor.getString(toIndex))
                .isFavourite(getJavaBooleanFromSqlBoolean(cursor.getInt(isFavouriteIndex)))
                .build();
    }

    private boolean getJavaBooleanFromSqlBoolean(int anInt) {
        return anInt == 1;
    }

    private int getSqlBooleanFromJavaBoolean(boolean bool) {
        return bool ? 1 : 0;
    }

    private void fillContentValuesFromWord(Word word, boolean isSaveInHistory) {
        contentValues.clear();
        contentValues.put(WORD, word.word());
        contentValues.put(TRANSLATION, word.translation());
        contentValues.put(LANGUAGE_FROM, word.languageFrom());
        contentValues.put(LANGUAGE_TO, word.languageTo());
        contentValues.put(IS_IN_HISTORY, getSqlBooleanFromJavaBoolean(isSaveInHistory));
        contentValues.put(IS_IN_FAVOURITES, getSqlBooleanFromJavaBoolean(word.isFavourite()));
        contentValues.put(ADD_DATE, System.currentTimeMillis());
    }

    public void clean(){
        String selection = String.format("%1s = ? AND %2s = ?", IS_IN_HISTORY, IS_IN_FAVOURITES);
        String[] selectionArgs = {FALSE, FALSE};
        database.delete(WORDS_TABLE, selection, selectionArgs);
    }
}
