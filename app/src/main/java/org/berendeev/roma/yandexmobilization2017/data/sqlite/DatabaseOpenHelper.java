package org.berendeev.roma.yandexmobilization2017.data.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


public class DatabaseOpenHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final String DATABASE_NAME = "yandexMobil2017.db";
    private static final int DATABASE_VERSION = 3;

    public static final String WORDS_TABLE = "words";
    public static final String WORD = "word";
    public static final String TRANSLATION = "translation";
    public static final String LANGUAGE_TO = "language_to";
    public static final String LANGUAGE_FROM = "language_from";
    public static final String IS_IN_HISTORY = "is_in_history";
    public static final String IS_IN_FAVOURITES = "is_in_favourites";
    public static final String ADD_DATE = "add_date";

    public DatabaseOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        String script = "create table " + WORDS_TABLE + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                WORD + " text not null, " +
                TRANSLATION + " text not null, " +
                LANGUAGE_FROM + " text not null, " +
                LANGUAGE_TO + " text not null, " +
                IS_IN_HISTORY + " integer not null, " +
                IS_IN_FAVOURITES + " integer not null, " +
                ADD_DATE + " integer not null);";
        db.execSQL(script);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WORDS_TABLE);
        onCreate(db);
    }

    @Override public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + WORDS_TABLE);
        onCreate(db);
    }
}
