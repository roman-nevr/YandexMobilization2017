package org.berendeev.roma.yandexmobilization2017.data.entity;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;

public class Languages {

    @SerializedName("dirs")
    @Expose
    public List<TranslateDirection> directions;
    @SerializedName("langs")
    @Expose
    public List<LanguageMap> languageMapList;

    @Override public String toString() {
        return "dirs: " + directions + ", languages: " + languageMapList;
    }
}