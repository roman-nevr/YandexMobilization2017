package org.berendeev.roma.yandexmobilization2017;

import android.content.Context;

import com.google.gson.Gson;

import org.berendeev.roma.yandexmobilization2017.data.http.CacheInterceptor;
import org.berendeev.roma.yandexmobilization2017.data.http.DictionaryApi;
import org.berendeev.roma.yandexmobilization2017.data.http.TranslateApi;
import org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper;
import org.berendeev.roma.yandexmobilization2017.data.sqlite.HistoryDataSource;
import org.berendeev.roma.yandexmobilization2017.di.DaggerTestMainComponent;
import org.berendeev.roma.yandexmobilization2017.di.MainModule;
import org.berendeev.roma.yandexmobilization2017.di.TestMainComponent;
import org.berendeev.roma.yandexmobilization2017.domain.DictionaryRepository;
import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;
import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetTranslationInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.TranslateTextInteractor;
import org.berendeev.roma.yandexmobilization2017.interactor.TestGetTranslationInteractor;
import org.berendeev.roma.yandexmobilization2017.interactor.TestTranslateTextInteractor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class InteractorTest {

    private Context context;
//    private TranslationRepository translationRepository;
//    private DictionaryRepository dictionaryRepository;
//    private HistoryAndFavouritesRepository historyAndFavouritesRepository;
//
//    private TestTranslateTextInteractor testTranslateTextInteractor;
//    private TestGetTranslationInteractor testGetTranslationInteractor;

    @Inject TranslateTextInteractor translateTextInteractor;
    @Inject GetTranslationInteractor getTranslationInteractor;
    @Inject ResultRepository resultRepository;

    @Before
    public void before() {
        context = RuntimeEnvironment.application.getApplicationContext();

        TestMainComponent mainComponent = DaggerTestMainComponent.builder().mainModule(new MainModule(context)).build();
        mainComponent.plusTestTranslatorComponent().inject(this);

    }

    @Test
    public void translateTest(){
        TranslationQuery query = TranslationQuery.create("time", "en", "ru");
        resultRepository.saveLastQuery(query).subscribe();
        translateTextInteractor.execute("time").subscribe();
        getTranslationInteractor.execute(null).subscribe(word -> {
            System.out.println(word);
            Assert.assertEquals(word.translation(), "время");
        });
    }


}
