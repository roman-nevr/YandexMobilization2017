package org.berendeev.roma.yandexmobilization2017.data.mapper;

import org.berendeev.roma.yandexmobilization2017.BuildConfig;
import org.berendeev.roma.yandexmobilization2017.data.entity.DictionaryTranslation;
import org.berendeev.roma.yandexmobilization2017.data.entity.HttpDefinition;
import org.berendeev.roma.yandexmobilization2017.data.entity.HttpDictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Definition;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;

import java.util.ArrayList;
import java.util.List;

public class DictionaryMapper {
    public static Dictionary map(HttpDictionary httpDictionary){
        try {
            String transcription;
            String text;
            if(httpDictionary.def.size() != 0){
                transcription = httpDictionary.def.get(0).ts;
                text = httpDictionary.def.get(0).text;
                if (transcription == null){
                    transcription = "";
                }
            }else {
                transcription = "";
                text = "";
            }
            List<Definition> definitions = new ArrayList<>();
            for (HttpDefinition httpDefinition : httpDictionary.def) {
                Definition definition = parseDefinition(httpDefinition);
                definitions.add(definition);
            }
            return Dictionary.builder()
                    .definitions(definitions)
                    .text(text)
                    .transcription(transcription)
                    .build();
        } catch (Exception e){
            if(BuildConfig.DEBUG){
                throw new IllegalArgumentException("DictionaryMapper exception");
            }
            return Dictionary.builder()  //если что-то непредвиденное
                    .definitions(new ArrayList<>())
                    .text("")
                    .transcription("")
                    .build();
        }
    }

    private static Definition parseDefinition(HttpDefinition httpDefinition){
        String speechPart = httpDefinition.pos;

        List<String> translations = new ArrayList<>();
        for (DictionaryTranslation dictionaryTranslation : httpDefinition.tr) {
            translations.add(dictionaryTranslation.text);
        }
        return Definition.builder()
                .speechPart(speechPart)
                .translations(translations)
                .build();
    }
}
