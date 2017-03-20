package org.berendeev.roma.yandexmobilization2017.domain.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class LanguageMap {
    public abstract String code();
    public abstract String name();



    @Override public String toString() {
        return code() + ":" + name();
    }

    public static LanguageMap create(String code, String name) {
        return builder()
                .code(code)
                .name(name)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_LanguageMap.Builder();
    }

    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder code(String code);

        public abstract Builder name(String name);

        public abstract LanguageMap build();
    }
}
