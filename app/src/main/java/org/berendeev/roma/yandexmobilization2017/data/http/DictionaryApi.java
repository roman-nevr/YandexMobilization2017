package org.berendeev.roma.yandexmobilization2017.data.http;

import org.berendeev.roma.yandexmobilization2017.data.entity.HttpDictionary;
import org.berendeev.roma.yandexmobilization2017.data.entity.Translation;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface DictionaryApi {
    public final String BASE_URL = "https://dictionary.yandex.net/api/v1/dicservice.json/";
    public static final int OK_CODE = 200;

    @GET
    Single<HttpDictionary> lookup(@Url String url);
}
//https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20170317T143823Z.7db79cf415a3a8b4.29bc3c1f3d9fd64c6ce17d12361c58263d475767&text=hello,world&lang=en-ru
//https://translate.yandex.net/api/v1.5/tr.json/getLangs?key=trnsl.1.1.20170317T143823Z.7db79cf415a3a8b4.29bc3c1f3d9fd64c6ce17d12361c58263d475767&ui=ru
//https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=dict.1.1.20170320T055703Z.8c96b153231390d8.6aca2f4ff53f2d8865537199a2d49932b57641a9&lang=en-ru&text=time