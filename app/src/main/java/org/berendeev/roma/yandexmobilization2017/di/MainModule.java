package org.berendeev.roma.yandexmobilization2017.di;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.berendeev.roma.yandexmobilization2017.BuildConfig;
import org.berendeev.roma.yandexmobilization2017.data.DictionaryRepositoryImpl;
import org.berendeev.roma.yandexmobilization2017.data.HistoryAndFavouritesRepositoryImpl;
import org.berendeev.roma.yandexmobilization2017.data.ResultRepositoryImpl;
import org.berendeev.roma.yandexmobilization2017.data.TranslationRepositoryImpl;
import org.berendeev.roma.yandexmobilization2017.data.deserializer.LanguageMapDeserializer;
import org.berendeev.roma.yandexmobilization2017.data.deserializer.MyAdapterFactory;
import org.berendeev.roma.yandexmobilization2017.data.deserializer.TranslateDirectionsDeserializer;
import org.berendeev.roma.yandexmobilization2017.data.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.data.http.CacheInterceptor;
import org.berendeev.roma.yandexmobilization2017.data.http.DictionaryApi;
import org.berendeev.roma.yandexmobilization2017.data.http.TranslateApi;
import org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseHistoryDataSource;
import org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper;
import org.berendeev.roma.yandexmobilization2017.data.sqlite.HistoryDataSource;
import org.berendeev.roma.yandexmobilization2017.domain.DictionaryRepository;
import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;
import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Definition;
import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class MainModule {

    private static final int HTTP_CACHE_SIZE = 1 * 1024 * 1024;

    private Context context;

    public MainModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return context;
    }



    @Singleton
    @Provides
    ThreadPoolExecutor provideThreadPoolExecutor(){
        int poolSize = 2;
        int maxPoolSize = 4;
        int timeout = 10;
        return new ThreadPoolExecutor(poolSize, maxPoolSize, timeout,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(5));
    }

    @Singleton
    @Provides Scheduler provideScheduler(){
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    public ResultRepository providePreferencesRepository(Context context, Gson gson){
        return new ResultRepositoryImpl(context, gson);
    }

    @Provides
    @Singleton
    public TranslationRepository provideTranslationRepository(TranslateApi translateApi, Context context, Gson gson){
        return new TranslationRepositoryImpl(translateApi, context, gson);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(CacheInterceptor cacheInterceptor, Context context) {
        File cacheDir = new File(context.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, HTTP_CACHE_SIZE);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(cacheInterceptor);
        if(BuildConfig.DEBUG){
            builder.addNetworkInterceptor(new StethoInterceptor());
        }

        return builder.build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient httpClient, Gson gson){
        return new Retrofit.Builder()
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(TranslateApi.BASE_URL)
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
                .registerTypeAdapterFactory(MyAdapterFactory.create())
                .create();
    }

    @Provides
    @Singleton
    public TranslateApi provideTranslateAPI(Retrofit retrofit){
        return retrofit.create(TranslateApi.class);
    }

    @Provides
    @Singleton
    public DictionaryApi provideDictionaryApi(Retrofit retrofit){
        return retrofit.create(DictionaryApi.class);
    }

    @Provides
    @Singleton
    public DictionaryRepository provideDictionaryRepository(DictionaryApi dictionaryApi){
        return new DictionaryRepositoryImpl(dictionaryApi);
    }

    @Provides
    @Singleton
    public CacheInterceptor provideCacheInterceptor(CacheControl cacheControl){
        return new CacheInterceptor(cacheControl);
    }

    @Provides
    @Singleton
    public CacheControl provideCacheControl(){
        return new CacheControl.Builder()
                .maxStale(1, TimeUnit.DAYS)
                .build();
    }

    @Provides
    @Singleton
    public HistoryAndFavouritesRepository provideHistoryAndFavouritesRepository(HistoryDataSource dataSource){
        return new HistoryAndFavouritesRepositoryImpl(dataSource);
    }

    @Provides
    @Singleton
    public HistoryDataSource provideHistoryDataSource(DatabaseOpenHelper openHelper, Gson gson){
        return new DatabaseHistoryDataSource(openHelper, gson);
    }

    @Provides
    @Singleton
    public DatabaseOpenHelper provideDatabaseOpenHelper(Context context){
        return new DatabaseOpenHelper(context);
    }


}
