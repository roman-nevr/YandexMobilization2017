package org.berendeev.roma.yandexmobilization2017.data;

import android.content.Context;

import org.berendeev.roma.yandexmobilization2017.BuildConfig;
import org.berendeev.roma.yandexmobilization2017.data.http.TranslateApi;
import org.berendeev.roma.yandexmobilization2017.data.mapper.LanguageMapper;
import org.berendeev.roma.yandexmobilization2017.data.mapper.TranslateMapper;
import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.domain.exception.TranslationException;

import java.util.Locale;

import io.reactivex.Observable;
import timber.log.Timber;

import static org.berendeev.roma.yandexmobilization2017.data.http.TranslateApi.OK_CODE;

public class TranslationRepositoryImpl implements TranslationRepository {


    TranslateApi translateApi;

    public TranslationRepositoryImpl(TranslateApi translateApi, Context context) {
        this.translateApi = translateApi;
    }

    @Override public Observable<Word> translate(TranslationQuery query) {
        return Observable.create(emitter -> translateApi
                .translate(BuildConfig.TRANSLATE_API_KEY, query.text(), query.langFrom() + "-" + query.langTo())
                .subscribe(translation -> {
                    if (translation.code == OK_CODE) {
                        Word word = TranslateMapper.map(query, translation);
                        emitter.onNext(word);
                        emitter.onComplete();
                    }else{
                        emitter.onError(new TranslationException());
                    }
                }, throwable ->
                        emitter.onError(throwable)));
    }

    @Override public Observable<LanguageMap> getLanguages(Locale locale) {
        return Observable.create(emitter -> translateApi
                .getLanguages(BuildConfig.TRANSLATE_API_KEY, locale.getLanguage())
                .subscribe(languages ->
                                emitter.onNext(LanguageMapper.map(languages, locale))
                        ,throwable ->
                                emitter.onError(throwable))
        );
    }

}
