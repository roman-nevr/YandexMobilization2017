package org.berendeev.roma.yandexmobilization2017.di;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.berendeev.roma.yandexmobilization2017.data.TranslationRepositoryImpl;
import org.berendeev.roma.yandexmobilization2017.data.http.TranslateAPI;
import org.berendeev.roma.yandexmobilization2017.data.deserializer.LanguageMapDeserializer;
import org.berendeev.roma.yandexmobilization2017.data.deserializer.TranslateDirectionsDeserializer;
import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
import org.berendeev.roma.yandexmobilization2017.data.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class MainModule {

    private Context context;

    public MainModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient httpClient){
        return new Retrofit.Builder()
                .client(httpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(TranslateAPI.BASE_URL)
                .build();
    }

    @Provides
    @Singleton
    public Gson provideGson(){
        Type dirType = new TypeToken<List<TranslateDirection>>() {}.getType();
        Type mapType = new TypeToken<List<LanguageMap>>() {}.getType();
        return new GsonBuilder()
                .registerTypeAdapter(mapType, new LanguageMapDeserializer())
                .registerTypeAdapter(dirType, new TranslateDirectionsDeserializer())
                .create();
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return context;
    }

    @Provides
    @Singleton
    public TranslationRepository provideRepository(){
        return new TranslationRepositoryImpl();
    }

}
