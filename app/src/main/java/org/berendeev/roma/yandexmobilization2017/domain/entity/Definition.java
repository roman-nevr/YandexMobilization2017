package org.berendeev.roma.yandexmobilization2017.domain.entity;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class Definition {
    public abstract List<String> translations();

    public abstract String speechPart();

    public static Builder builder() {
        return new AutoValue_Definition.Builder();
    }


    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder translations(List<String> translations);

        public abstract Builder speechPart(String speechPart);

        public abstract Definition build();
    }
}
