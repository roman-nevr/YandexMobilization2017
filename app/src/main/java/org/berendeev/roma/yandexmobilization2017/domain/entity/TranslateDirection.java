package org.berendeev.roma.yandexmobilization2017.domain.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class TranslateDirection {
    public abstract String key();

    public abstract String name();

    public static Builder builder() {
        return new AutoValue_TranslateDirection.Builder();
    }


    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder key(String key);

        public abstract Builder name(String name);

        public abstract TranslateDirection build();
    }
}
