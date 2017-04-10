package org.berendeev.roma.yandexmobilization2017.data.http;

import org.berendeev.roma.yandexmobilization2017.data.entity.Languages;
import org.berendeev.roma.yandexmobilization2017.data.entity.Translation;
import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TranslateApi {
    String BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";
    int OK_CODE = 200;


    @GET("translate")
    Single<Translation> translate(@Query("key") String key,
                                       @Query("text") String text,
                                       @Query("lang") String langDirections);

    @GET("getLangs")
    Single<Languages> getLanguages(@Query("key") String key,
                                   @Query("ui") String uiLanguage);
}
//https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20170317T143823Z.7db79cf415a3a8b4.29bc3c1f3d9fd64c6ce17d12361c58263d475767&text=hello,world&lang=en-ru
//https://translate.yandex.net/api/v1.5/tr.json/getLangs?key=trnsl.1.1.20170317T143823Z.7db79cf415a3a8b4.29bc3c1f3d9fd64c6ce17d12361c58263d475767&ui=ru
//https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=dict.1.1.20170320T055703Z.8c96b153231390d8.6aca2f4ff53f2d8865537199a2d49932b57641a9&lang=en-ru&text=time