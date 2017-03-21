package org.berendeev.roma.yandexmobilization2017;

import android.content.Context;

import org.berendeev.roma.yandexmobilization2017.data.TranslationRepositoryImpl;
import org.berendeev.roma.yandexmobilization2017.data.http.CacheInterceptor;
import org.berendeev.roma.yandexmobilization2017.data.http.TranslateAPI;
import org.berendeev.roma.yandexmobilization2017.di.MainModule;
import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Locale;

import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@RunWith(RobolectricTestRunner.class)
public class RepositoryTest {

    private Context application;
    private TranslationRepository repository;

    @Before
    public void before() {
        application = RuntimeEnvironment.application.getApplicationContext();
        MainModule mainModule = new MainModule(application.getApplicationContext());
        CacheControl cacheControl = mainModule.provideCacheControl();
        CacheInterceptor interceptor = mainModule.provideCacheInterceptor(cacheControl);
        OkHttpClient httpClient = mainModule.provideOkHttpClient(interceptor);
        Retrofit retrofit = mainModule.provideRetrofit(httpClient, mainModule.provideGson());
        TranslateAPI translateAPI = mainModule.provideTranslateAPI(retrofit);
        repository = new TranslationRepositoryImpl(translateAPI, mainModule.provideContext());
    }

    @Test
    public void langTest() {
        repository
                .getLanguages(Locale.getDefault())
                .subscribe(languageMap -> {
            System.out.println(languageMap);
        });
    }

    @Test
    public void translateTest(){
        TranslationQuery query = TranslationQuery.create("hello, world", "en", "ru");
        repository
                .translate(query)
                .subscribe(word ->{
                    System.out.println(word);
                });
    }

    @Test
    public void lastWord(){
        translateTest();
        repository
                .getLastWord()
                .subscribe(word -> {
                    System.out.println(word);
                });
    }

}
