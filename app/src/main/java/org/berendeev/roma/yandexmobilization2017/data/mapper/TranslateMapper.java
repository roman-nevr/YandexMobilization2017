package org.berendeev.roma.yandexmobilization2017.data.mapper;

import org.berendeev.roma.yandexmobilization2017.data.entity.Translation;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

public class TranslateMapper {
    public static Word map(TranslationQuery query, Translation translation, Dictionary dictionary){
        String[] langs = translation.lang.split("-");
        return Word.create(query.text(), translation.translation.get(0), langs[0], langs[1], false, dictionary, Word.TranslationState.received);
    }
}
