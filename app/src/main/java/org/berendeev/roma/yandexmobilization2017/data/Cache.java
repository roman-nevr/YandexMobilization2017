package org.berendeev.roma.yandexmobilization2017.data;

import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;
import java.util.Locale;

public interface Cache {
    void persist(Word query, String json);
    void persist(Locale locale, String json);
}
