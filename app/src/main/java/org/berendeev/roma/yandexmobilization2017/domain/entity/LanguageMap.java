package org.berendeev.roma.yandexmobilization2017.domain.entity;

import com.google.auto.value.AutoValue;

import java.util.Locale;
import java.util.Map;


@AutoValue
public abstract class LanguageMap{
    public abstract Locale local();
    public abstract Map<String, String> map();

    public static Builder builder() {
        return new AutoValue_LanguageMap.Builder();
    }

    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder local(Locale local);

        public abstract Builder map(Map<String, String> map);

        public abstract LanguageMap build();
    }
}
