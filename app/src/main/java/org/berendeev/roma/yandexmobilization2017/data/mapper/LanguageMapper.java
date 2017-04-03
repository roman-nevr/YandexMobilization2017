package org.berendeev.roma.yandexmobilization2017.data.mapper;

import org.berendeev.roma.yandexmobilization2017.data.entity.Languages;
import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;

import java.util.Locale;

public class LanguageMapper {
    public static LanguageMap map(Languages languages, Locale locale) {
        return LanguageMap.builder()
                .locale(locale)
                .map(languages.languageMapList)
                .build();
    }
}
