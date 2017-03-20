package org.berendeev.roma.yandexmobilization2017;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.berendeev.roma.yandexmobilization2017.data.deserializer.LanguageMapDeserializer;
import org.berendeev.roma.yandexmobilization2017.data.deserializer.TranslateDirectionsDeserializer;
import org.berendeev.roma.yandexmobilization2017.data.entity.LanguageMap;
import org.berendeev.roma.yandexmobilization2017.data.entity.Languages;
import org.berendeev.roma.yandexmobilization2017.data.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.data.entity.Translation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Arrays;

public class DeserializatorTest {
    private static final String langsJson ="{\"dirs\":[\"uk-sr\",\"uk-tr\"],\"langs\":{\"ja\":\"Japanese\",\"ru\":\"Russian\"}}";
    private static final String translateJson = "{\"code\":200,\"lang\":\"en-ru\",\"text\":[\"привет,мир\"]}";
    private Type dirType;
    private Type mapType;
    private Gson gson;

    @Before
    public void before() {
        dirType = new TypeToken<List<TranslateDirection>>() {}.getType();
        mapType = new TypeToken<List<LanguageMap>>() {}.getType();

        gson = new GsonBuilder()
                .registerTypeAdapter(mapType, new LanguageMapDeserializer())
                .registerTypeAdapter(dirType, new TranslateDirectionsDeserializer())
                .create();
    }

    @Test
    public void langTest() {
        Languages languages = new Languages();
        languages.directions = new ArrayList<>();
        languages.directions.add(new TranslateDirection("uk", "sr"));
        languages.directions.add(new TranslateDirection("uk", "tr"));
        languages.languageMapList = new ArrayList<>();
        languages.languageMapList.add(new LanguageMap("uk", "Ukranian"));
        languages.languageMapList.add(new LanguageMap("tr", "Hz"));

//        Assert.assertTrue(languages.equals());
        System.out.println(gson.fromJson(langsJson, Languages.class));
    }

    @Test
    public void translateTest(){
        System.out.println(gson.fromJson(translateJson, Translation.class));
    }
}
