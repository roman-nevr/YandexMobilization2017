package org.berendeev.roma.yandexmobilization2017.data.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.berendeev.roma.yandexmobilization2017.data.entity.LanguageMap;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LanguageMapDeserializer implements JsonDeserializer<List<LanguageMap>> {
    @Override public List<LanguageMap> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<LanguageMap> languageMapList = new ArrayList<>();
        JsonObject jsonObject = json.getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            languageMapList.add(new LanguageMap(entry.getKey(), context.deserialize(entry.getValue(), String.class)));
        }
        return languageMapList;
    }
}
