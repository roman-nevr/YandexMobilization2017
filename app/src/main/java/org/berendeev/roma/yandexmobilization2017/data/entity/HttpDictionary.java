package org.berendeev.roma.yandexmobilization2017.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HttpDictionary {
    @SerializedName("def")
    @Expose
    public List<HttpDefinition> def = null;
}
