package org.berendeev.roma.yandexmobilization2017;

import android.content.Context;

import com.google.gson.Gson;

import org.berendeev.roma.yandexmobilization2017.data.http.CacheInterceptor;
import org.berendeev.roma.yandexmobilization2017.data.http.DictionaryApi;
import org.berendeev.roma.yandexmobilization2017.data.mapper.DictionaryMapper;
import org.berendeev.roma.yandexmobilization2017.di.MainModule;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE)
public class HttpDictionaryTest {

    private DictionaryApi api;
    public static final String KEY = "dict.1.1.20170320T055703Z.8c96b153231390d8.6aca2f4ff53f2d8865537199a2d49932b57641a9";

    @Before
    public void before(){
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
    public void apiTest(){
        String url = String.format("%1slookup?key=%2s&text=%3s&lang=%4s", DictionaryApi.BASE_URL, KEY, "time", "en-ru");
        api.lookup(url).subscribe(translation -> {
            Dictionary dictionary = DictionaryMapper.map(translation);
            System.out.println(dictionary);
        });
    }
}
