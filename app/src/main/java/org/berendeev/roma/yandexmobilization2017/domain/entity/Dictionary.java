package org.berendeev.roma.yandexmobilization2017.domain.entity;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class Dictionary {
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
}
