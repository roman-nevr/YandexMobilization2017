package org.berendeev.roma.yandexmobilization2017.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HttpDefinition {
    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("pos")
    @Expose
    public String pos;
    @SerializedName("ts")
    @Expose
    public String ts;
    @SerializedName("tr")
    @Expose
    public List<DictionaryTranslation> tr = null;
}
