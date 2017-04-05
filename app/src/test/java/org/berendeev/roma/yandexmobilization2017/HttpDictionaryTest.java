package org.berendeev.roma.yandexmobilization2017;

import android.content.Context;

import com.google.gson.Gson;

import org.berendeev.roma.yandexmobilization2017.data.entity.HttpDictionary;
import org.berendeev.roma.yandexmobilization2017.data.http.CacheInterceptor;
import org.berendeev.roma.yandexmobilization2017.data.http.DictionaryApi;
import org.berendeev.roma.yandexmobilization2017.data.mapper.DictionaryMapper;
import org.berendeev.roma.yandexmobilization2017.di.MainModule;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscription;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

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

    @Before
    public void before() {
        Context context = RuntimeEnvironment.application.getApplicationContext();
        MainModule mainModule = new MainModule(context.getApplicationContext());

        CacheControl cacheControl = mainModule.provideCacheControl();
        CacheInterceptor cacheInterceptor = mainModule.provideCacheInterceptor(cacheControl);
        OkHttpClient httpClient = mainModule.provideOkHttpClient(cacheInterceptor, context);
        Gson gson = mainModule.provideGson();
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
                    .blockingFirst();
            emitter.onNext(httpDictionary);
        });
        Disposable disposable = objectObservable
                .subscribeOn(Schedulers.io())
                .observeOn(new TestScheduler())
                .subscribe(httpDictionary -> {
                    Dictionary dictionary = DictionaryMapper.map(httpDictionary);
                    System.out.println(dictionary);
                });
        disposable.dispose();
        Thread.sleep(1000);
    }
}
