package org.berendeev.roma.yandexmobilization2017.data;

import org.berendeev.roma.yandexmobilization2017.BuildConfig;
import org.berendeev.roma.yandexmobilization2017.data.entity.HttpDictionary;
import org.berendeev.roma.yandexmobilization2017.data.http.DictionaryApi;
import org.berendeev.roma.yandexmobilization2017.data.mapper.DictionaryMapper;
import org.berendeev.roma.yandexmobilization2017.domain.DictionaryRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.domain.exception.ConnectionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import timber.log.Timber;


public class DictionaryRepositoryImpl implements DictionaryRepository {

    private DictionaryApi dictionaryApi;
    private List<String> uiLangs = new ArrayList<>(Arrays.asList("ru", "uk", "tk"));

    public DictionaryRepositoryImpl(DictionaryApi dictionaryApi) {
        this.dictionaryApi = dictionaryApi;
    }

    @Override public Observable<Dictionary> lookup(TranslationQuery query) {
        if (query.text().equals("")) {
            return Observable.just(Dictionary.builder()
                    .text("")
                    .transcription("")
                    .definitions(new ArrayList<>())
                    .build());
        }
        String url = String.format("%slookup?key=%s&text=%s&lang=%s&ui=%s", DictionaryApi.BASE_URL,
                BuildConfig.DICTIONARY_API_KEY, query.text(), query.langFrom() + "-" + query.langTo(),
                getUiLanguage());

        return Observable.fromCallable(() -> {
            try {
                HttpDictionary httpDictionary = dictionaryApi
                        .lookup(url)
                        .blockingGet();
                return DictionaryMapper.map(httpDictionary);
            }catch (Throwable throwable){
                Timber.d(throwable, "wft");
                throw new ConnectionException(throwable);
//                return null;
            }
        });
    }

    private String getUiLanguage() {
        int index = uiLangs.indexOf(Locale.getDefault().getLanguage());
        if (index == -1) {
            return "en";
        } else {
            return uiLangs.get(index);
        }
    }
}
