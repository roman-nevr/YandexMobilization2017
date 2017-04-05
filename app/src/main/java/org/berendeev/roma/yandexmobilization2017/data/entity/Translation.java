package org.berendeev.roma.yandexmobilization2017.data.entity;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Translation {

    @SerializedName("code")
    @Expose
    public Integer code;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("lang")
    @Expose
    public String lang;
    @SerializedName("text")
    @Expose
    public List<String> translation = null;

    public Translation() {
    }

    public Translation(Integer code, String lang, List<String> translation) {
        super();
        this.code = code;
        this.lang = lang;
        this.translation = translation;
    }

    @Override public String toString() {
        return "code: " + code + ", lang: " + lang + ", text: " + translation;
    }
}
//https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20170317T143823Z.7db79cf415a3a8b4.29bc3c1f3d9fd64c6ce17d12361c58263d475767&text=hello,world&lang=en-ru
//{"code":200,"lang":"en-ru","text":["привет,мир"]}