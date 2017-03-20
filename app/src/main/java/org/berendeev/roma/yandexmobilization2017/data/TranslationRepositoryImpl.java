package org.berendeev.roma.yandexmobilization2017.data;

import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

import io.reactivex.Observable;

public class TranslationRepositoryImpl implements TranslationRepository {
    @Override public Observable<Word> translate(Word word) {
        return null;
    }

    @Override public Observable<List<LanguageMap>> getLanguages(String locale) {
        return null;
    }

    @Override public Observable<Word> getLastWord() {
        return null;
    }
}
