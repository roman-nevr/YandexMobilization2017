package org.berendeev.roma.yandexmobilization2017.data.http;

import android.content.Context;

import com.google.gson.Gson;

import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.data.entity.Languages;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class OfflineLanguages {

    public static final String RU = "ru";
    public static final String EN = "en";

    private static int ru = R.raw.ru;
    private static int en = R.raw.en;

    public static final Map<String, Integer> offlineLanguages = new HashMap<>();

    static {
        offlineLanguages.put(RU, ru);
        offlineLanguages.put(EN, en);
    }

    public static Languages getLanguage(Context context, String lang, Gson gson){
        int resId;
        if (OfflineLanguages.offlineLanguages.containsKey(lang)){
            resId = offlineLanguages.get(lang);
        }
        else{
            resId = offlineLanguages.get(EN);
        }

        InputStream inputStream = context.getResources().openRawResource(resId);
        InputStreamReader reader = new InputStreamReader(inputStream);
        return gson.fromJson(reader, Languages.class);
    }

}
