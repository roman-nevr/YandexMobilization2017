package org.berendeev.roma.yandexmobilization2017.domain.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Word {

    public abstract String word();
    public abstract String translation();

    public abstract String languageFrom();
    public abstract String languageTo();

    public abstract boolean isFavourite();

    public static Word create(String word, String translation, String languageFrom, String languageTo, boolean isFavourite) {
        return builder()
                .word(word)
                .translation(translation)
                .languageFrom(languageFrom)
                .languageTo(languageTo)
                .isFavourite(isFavourite)
                .build();
    }

    public abstract Builder toBuilder();

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Word) {
            Word that = (Word) o;
            return (word().equals(that.word()))
                    && (languageFrom().equals(that.languageFrom()))
                    && (languageTo().equals(that.languageTo()));
        }
        return false;
    }

    public static Builder builder() {
        return new AutoValue_Word.Builder();
    }

    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder word(String word);

        public abstract Builder translation(String translation);

        public abstract Builder languageFrom(String languageFrom);

        public abstract Builder languageTo(String languageTo);

        public abstract Builder isFavourite(boolean isFavourite);

        public abstract Word build();
    }
}
