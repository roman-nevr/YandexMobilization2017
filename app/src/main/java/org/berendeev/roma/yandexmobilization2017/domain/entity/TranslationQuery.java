package org.berendeev.roma.yandexmobilization2017.domain.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class TranslationQuery {
    public abstract String text();
    public abstract String langFrom();
    public abstract String langTo();

    public static TranslationQuery create(String text, String langFrom, String langTo) {
        return builder()
                .text(text)
                .langFrom(langFrom)
                .langTo(langTo)
                .build();
    }

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_TranslationQuery.Builder();
    }


    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder text(String text);

        public abstract Builder langFrom(String langFrom);

        public abstract Builder langTo(String langTo);

        public abstract TranslationQuery build();
    }
}
