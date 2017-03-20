package org.berendeev.roma.yandexmobilization2017.domain;

import android.content.res.Configuration;

import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

import io.reactivex.Observable;

public interface TranslationRepository {
    Observable<Word> translate(Word word);
    Observable<List<LanguageMap>> getLanguages(String locale);
    Observable<Word> getLastWord();
}
