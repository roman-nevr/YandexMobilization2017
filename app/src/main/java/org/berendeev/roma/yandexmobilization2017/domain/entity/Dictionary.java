package org.berendeev.roma.yandexmobilization2017.domain.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.ryanharter.auto.value.gson.GsonTypeAdapter;

import java.util.ArrayList;
import java.util.List;

@AutoValue
public abstract class Dictionary {

    public static Dictionary EMPTY = builder().text("").transcription("").definitions(new ArrayList<>()).build();

    public abstract String text();

    public abstract String transcription();

    public abstract List<Definition> definitions();

    public static Builder builder() {
        return new AutoValue_Dictionary.Builder();
    }


    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder text(String text);

        public abstract Builder transcription(String transcription);

        public abstract Builder definitions(List<Definition> definitions);

        public abstract Dictionary build();
    }

    public static TypeAdapter<Dictionary> typeAdapter(Gson gson) {
        return new AutoValue_Dictionary.GsonTypeAdapter(gson);
    }
}
