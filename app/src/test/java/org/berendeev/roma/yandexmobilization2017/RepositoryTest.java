package org.berendeev.roma.yandexmobilization2017;

import android.content.Context;

import org.berendeev.roma.yandexmobilization2017.data.TranslationRepositoryImpl;
import org.berendeev.roma.yandexmobilization2017.data.http.CacheInterceptor;
import org.berendeev.roma.yandexmobilization2017.data.http.TranslateApi;
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

    private Context context;
    private TranslationRepository repository;

    @Before
    public void before() {
        context = RuntimeEnvironment.application.getApplicationContext();
        MainModule mainModule = new MainModule(context.getApplicationContext());
        CacheControl cacheControl = mainModule.provideCacheControl();
        CacheInterceptor interceptor = mainModule.provideCacheInterceptor(cacheControl);
        OkHttpClient httpClient = mainModule.provideOkHttpClient(interceptor, mainModule.provideContext());
        Retrofit retrofit = mainModule.provideRetrofit(httpClient, mainModule.provideGson());
        TranslateApi translateApi = mainModule.provideTranslateAPI(retrofit);
        repository = new TranslationRepositoryImpl(translateApi, mainModule.provideContext());
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

}
