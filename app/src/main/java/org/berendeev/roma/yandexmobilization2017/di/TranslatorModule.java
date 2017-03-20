package org.berendeev.roma.yandexmobilization2017.di;

import org.berendeev.roma.yandexmobilization2017.data.datasource.TranslateAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class TranslatorModule {

    @TranslatorScope
    @Provides TranslateAPI provideTranslateAPI(Retrofit retrofit){
        return retrofit.create(TranslateAPI.class);
    }
}
