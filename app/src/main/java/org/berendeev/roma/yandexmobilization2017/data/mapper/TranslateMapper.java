package org.berendeev.roma.yandexmobilization2017.data.mapper;

import org.berendeev.roma.yandexmobilization2017.data.entity.Translation;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

public class TranslateMapper {
    public static Word map(TranslationQuery query, Translation translation, Dictionary dictionary){
        String[] langs = translation.lang.split("-");
        String translationResult;
        if(translation.translation.size() == 0){
            translationResult = "";
        }else{
            translationResult = translation.translation.get(0);
        }
        return Word.create(query.text(), translationResult, langs[0], langs[1], false, dictionary, Word.TranslationState.ok, System.currentTimeMillis());
    }
}
