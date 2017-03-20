package org.berendeev.roma.yandexmobilization2017.data.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.berendeev.roma.yandexmobilization2017.data.entity.Languages;

import java.lang.reflect.Type;

public class LanguageDeserializer implements JsonDeserializer<Languages> {
    @Override public Languages deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Languages languages = new Languages();
        return null;
    }
}
