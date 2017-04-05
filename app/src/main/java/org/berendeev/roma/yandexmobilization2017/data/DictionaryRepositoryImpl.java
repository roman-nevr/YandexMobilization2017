package org.berendeev.roma.yandexmobilization2017.data;

import org.berendeev.roma.yandexmobilization2017.BuildConfig;
import org.berendeev.roma.yandexmobilization2017.data.http.DictionaryApi;
import org.berendeev.roma.yandexmobilization2017.data.mapper.DictionaryMapper;
import org.berendeev.roma.yandexmobilization2017.domain.DictionaryRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;


public class DictionaryRepositoryImpl implements DictionaryRepository {

    private DictionaryApi dictionaryApi;
    private List<String> uiLangs = new ArrayList<>(Arrays.asList("ru", "uk", "tk"));

    public DictionaryRepositoryImpl(DictionaryApi dictionaryApi) {
        this.dictionaryApi = dictionaryApi;
    }

    @Override public Observable<Dictionary> lookup(TranslationQuery query) {
        String url = String.format("%slookup?key=%s&text=%s&lang=%s&ui=%s", DictionaryApi.BASE_URL,
                BuildConfig.DICTIONARY_API_KEY, query.text(), query.langFrom() + "-" + query.langTo(),
                getUiLanguage());

        return dictionaryApi
                .lookup(url)
                .map(httpDictionary -> DictionaryMapper.map(httpDictionary));
    }

    private String getUiLanguage(){
        int index = uiLangs.indexOf(Locale.getDefault().getLanguage());
        if(index == -1){
            return "en";
        }else {
            return uiLangs.get(index);
        }
    }
}