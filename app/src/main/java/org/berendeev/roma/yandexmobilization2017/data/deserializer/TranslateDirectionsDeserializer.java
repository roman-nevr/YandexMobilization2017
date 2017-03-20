package org.berendeev.roma.yandexmobilization2017.data.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.berendeev.roma.yandexmobilization2017.data.entity.LanguageMap;
import org.berendeev.roma.yandexmobilization2017.data.entity.TranslateDirection;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TranslateDirectionsDeserializer implements JsonDeserializer<List<TranslateDirection>> {

    @Override public List<TranslateDirection> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<TranslateDirection> directions = new ArrayList<>();
        JsonArray jsonArray = json.getAsJsonArray();
        for (JsonElement item : jsonArray) {
            String[] langs = item.getAsString().split("-");
            directions.add(new TranslateDirection(langs[0], langs[1]));
        }
        return directions;
    }
}
