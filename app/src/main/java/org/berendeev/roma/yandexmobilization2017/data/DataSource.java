package org.berendeev.roma.yandexmobilization2017.data;

import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.Locale;

import io.reactivex.Observable;

public interface DataSource {
    Observable<String> getTranslation(Word word);
    Observable<String> getLanguages(Locale locale);
    Observable<Word> getLastWord();
}
