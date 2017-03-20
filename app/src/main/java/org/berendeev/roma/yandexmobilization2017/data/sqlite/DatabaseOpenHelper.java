package org.berendeev.roma.yandexmobilization2017.data.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


public class DatabaseOpenHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final String DATABASE_NAME = "yandexMobil2017.db";
    private static final int DATABASE_VERSION = 1;

    public static final String NAME = "name";

    public static final String WORDS_TABLE = "todos";
    public static final String WORD = "word";
    public static final String TRANSLATION = "translation";
    public static final String LANGUAGE_TO_ID = "language_to_id";
    public static final String LANGUAGE_FROM_ID = "language_from_id";


    public static final String LANGUAGES_TABLE = "languages";
    public static final String LANGUAGE = "language";

    public DatabaseOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        String script = "create table " + WORDS_TABLE + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                WORD + " text not null, " +
                TRANSLATION + " text not null, " +
                LANGUAGE_FROM_ID + " integer not null, " +
                LANGUAGE_TO_ID + " integer not null);";
        db.execSQL(script);
        script = "create table " + LANGUAGES_TABLE + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                LANGUAGE + " integer not null);";
        db.execSQL(script);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WORDS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LANGUAGES_TABLE);
        onCreate(db);
    }

    @Override public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + WORDS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LANGUAGES_TABLE);
        onCreate(db);
    }
}
