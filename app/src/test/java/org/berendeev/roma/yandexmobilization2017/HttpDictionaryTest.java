package org.berendeev.roma.yandexmobilization2017;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.berendeev.roma.yandexmobilization2017.data.deserializer.LanguageMapDeserializer;
import org.berendeev.roma.yandexmobilization2017.data.deserializer.MyAdapterFactory;
import org.berendeev.roma.yandexmobilization2017.data.deserializer.TranslateDirectionsDeserializer;
import org.berendeev.roma.yandexmobilization2017.data.entity.HttpDictionary;
import org.berendeev.roma.yandexmobilization2017.data.entity.Languages;
import org.berendeev.roma.yandexmobilization2017.data.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.data.http.CacheInterceptor;
import org.berendeev.roma.yandexmobilization2017.data.http.DictionaryApi;
import org.berendeev.roma.yandexmobilization2017.data.http.OfflineLanguages;
import org.berendeev.roma.yandexmobilization2017.data.mapper.DictionaryMapper;
import org.berendeev.roma.yandexmobilization2017.data.mapper.LanguageMapper;
import org.berendeev.roma.yandexmobilization2017.di.MainModule;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscription;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class HttpDictionaryTest {

    private DictionaryApi api;
    private Gson gson;
    private Type mapType;

    @Before
    public void before() {
        Context context = RuntimeEnvironment.application.getApplicationContext();
        MainModule mainModule = new MainModule(context.getApplicationContext());

        CacheControl cacheControl = mainModule.provideCacheControl();
        CacheInterceptor cacheInterceptor = mainModule.provideCacheInterceptor(cacheControl);
        OkHttpClient httpClient = mainModule.provideOkHttpClient(cacheInterceptor, context);
        Type dirType = new TypeToken<List<TranslateDirection>>() {}.getType();
        mapType = new TypeToken<LanguageMap>() {}.getType();
        gson = mainModule.provideGson();
        Retrofit retrofit = mainModule.provideRetrofit(httpClient, gson);
        api = mainModule.provideDictionaryApi(retrofit);
        System.out.println("");
    }

    @Test
    public void apiTest() throws InterruptedException {
        String url = String.format("%1slookup?key=%2s&text=%3s&lang=%4s", DictionaryApi.BASE_URL, BuildConfig.DICTIONARY_API_KEY, "time", "en-ru");
        Observable<HttpDictionary> objectObservable = Observable.create(emitter -> {
            HttpDictionary httpDictionary = api
                    .lookup(url)
                    .delay(100, TimeUnit.MILLISECONDS)
                    .blockingGet();
            emitter.onNext(httpDictionary);
        });
        Disposable disposable = objectObservable
                .subscribeOn(Schedulers.io())
                .subscribe(httpDictionary -> {
                    Dictionary dictionary = DictionaryMapper.map(httpDictionary);
                    String json = gson.toJson(dictionary, Dictionary.class);
                    Dictionary dictionary2 = gson.fromJson(json, Dictionary.class);
                    System.out.println(dictionary);
                    Assert.assertTrue(dictionary2.equals(dictionary));
                });
        //disposable.dispose();
        Thread.sleep(100000);
    }

    @Test
    public void gsonTest(){
        Locale locale = Locale.getDefault();
        Languages languages = gson.fromJson(OfflineLanguages.offlineLanguages.get(locale.getLanguage()), Languages.class);
        LanguageMap map = LanguageMapper.map(languages, locale);
        System.out.println(gson.fromJson(OfflineLanguages.offlineLanguages.get("ru"), Languages.class));
    }
}
