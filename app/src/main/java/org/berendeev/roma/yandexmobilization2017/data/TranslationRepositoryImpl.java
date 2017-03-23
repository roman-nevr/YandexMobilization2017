package org.berendeev.roma.yandexmobilization2017.data;

import android.content.Context;

import org.berendeev.roma.yandexmobilization2017.data.entity.Languages;
import org.berendeev.roma.yandexmobilization2017.data.entity.Translation;
import org.berendeev.roma.yandexmobilization2017.data.http.TranslateAPI;
import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;


import io.reactivex.Observable;
import timber.log.Timber;

import static org.berendeev.roma.yandexmobilization2017.Consts.API_KEY;
import static org.berendeev.roma.yandexmobilization2017.data.http.TranslateAPI.OK_CODE;

public class TranslationRepositoryImpl implements TranslationRepository {


    TranslateAPI translateAPI;

    public TranslationRepositoryImpl(TranslateAPI translateAPI, Context context) {
        this.translateAPI = translateAPI;
    }

    @Override public Observable<Word> translate(TranslationQuery query) {
        return Observable.create(emitter -> translateAPI
                .translate(API_KEY, query.text(), query.langFrom() + "-" + query.langTo())
                .subscribe(translation -> {
                    if (translation.code == OK_CODE) {
                        Word word = buildWord(query, translation);
                        emitter.onNext(word);
                    }
                    emitter.onComplete();
                }, throwable -> Timber.d(throwable)));
    }

    @Override public Observable<LanguageMap> getLanguages(Locale locale) {
        return Observable.create(emitter -> translateAPI
                .getLanguages(API_KEY, locale.getLanguage())
                .subscribe(languages ->
                                emitter.onNext(buildLanguageMap(languages, locale)),
                        throwable -> {
                        })
        );
    }

    private LanguageMap buildLanguageMap(Languages languages, Locale locale) {
        return LanguageMap.builder()
                .locale(locale)
                .map(languages.languageMapList)
                .build();
    }

    private Word buildWord(TranslationQuery query, Translation translation) {
        String[] langs = translation.lang.split("-");
        return Word.create(query.text(), createStringWithWrapping(translation.translation), langs[0], langs[1], false);
    }

    private String createStringWithWrapping(List<String> list) {
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
            if (iterator.hasNext()) {
                builder.append("\\n");
            }
        }
        return builder.toString();
    }
}
