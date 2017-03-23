package org.berendeev.roma.yandexmobilization2017.di;

import android.content.Context;

import org.berendeev.roma.yandexmobilization2017.data.HistoryAndFavouritesRepositoryImpl;
import org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseHistoryDataSource;
import org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper;
import org.berendeev.roma.yandexmobilization2017.data.sqlite.HistoryDataSource;
import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class HistoryAndFavouritesModule {
    @Provides
    @ListScope
    public HistoryAndFavouritesRepository provideHistoryAndFavouritesRepository(HistoryDataSource dataSource){
        return new HistoryAndFavouritesRepositoryImpl(dataSource);
    }

    @Provides
    @ListScope
    public HistoryDataSource provideHistoryDataSource(DatabaseOpenHelper openHelper){
        return new DatabaseHistoryDataSource(openHelper);
    }

    @Provides
    @ListScope
    public DatabaseOpenHelper provideDatabaseOpenHelper(Context context){
        return new DatabaseOpenHelper(context);
    }
}
