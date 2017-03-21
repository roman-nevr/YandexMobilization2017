package org.berendeev.roma.yandexmobilization2017.domain;

import android.content.res.Configuration;
import android.util.Pair;

import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;

public interface TranslationRepository {
    Observable<Word> translate(TranslationQuery word);
    Observable<LanguageMap> getLanguages(Locale locale);
    Observable<Word> getLastWord();
}
