package org.berendeev.roma.yandexmobilization2017.data;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.berendeev.roma.yandexmobilization2017.BuildConfig;
import org.berendeev.roma.yandexmobilization2017.data.entity.HttpDictionary;
import org.berendeev.roma.yandexmobilization2017.data.http.DictionaryApi;
import org.berendeev.roma.yandexmobilization2017.data.mapper.DictionaryMapper;
import org.berendeev.roma.yandexmobilization2017.domain.DictionaryRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.domain.exception.ConnectionException;
import org.berendeev.roma.yandexmobilization2017.rx.LongSingle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Single;
import timber.log.Timber;


public class DictionaryRepositoryImpl implements DictionaryRepository {

    //Реализация интерфейса репозитария для словаря
    //Точнее это источник данных словаря, хранения здесь нет

    private DictionaryApi dictionaryApi;

    public DictionaryRepositoryImpl(DictionaryApi dictionaryApi) {
        this.dictionaryApi = dictionaryApi;
    }

    @Override public Single<Dictionary> lookup(TranslationQuery query) {
        if (query.text().equals("")) {
            return Single.just(Dictionary.EMPTY);
        }
        String url = String.format("%slookup?key=%s&text=%s&lang=%s&ui=%s", DictionaryApi.BASE_URL,
                BuildConfig.DICTIONARY_API_KEY, query.text(), query.langFrom() + "-" + query.langTo(),
                getUiLanguage());

        return LongSingle.fromCallable(() -> {
            try {
                HttpDictionary httpDictionary = dictionaryApi
                        .lookup(url)
                        .blockingGet();
                return DictionaryMapper.map(httpDictionary);
            }catch (Throwable throwable){
                if(throwable.getCause() instanceof HttpException){
                    return Dictionary.EMPTY;
                }
                Timber.wtf(throwable, "wft");
                throw new ConnectionException(throwable);
            }
        });
    }

    private String getUiLanguage() {
        return Locale.getDefault().getLanguage();
    }
}
