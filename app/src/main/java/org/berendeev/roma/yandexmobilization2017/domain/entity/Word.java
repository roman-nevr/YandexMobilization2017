package org.berendeev.roma.yandexmobilization2017.domain.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Word {

    public static Word EMPTY = create("", "", "", "", false, Dictionary.EMPTY, TranslationState.ok);

    public abstract String word();

    public abstract String translation();

    public abstract String languageFrom();

    public abstract String languageTo();

    public abstract boolean isFavourite();

    public abstract Dictionary dictionary();

    public abstract TranslationState translationState();

    public enum TranslationState {
        requested, ok, connectionError, translationError
    }

    public static Word create(String word, String translation, String languageFrom, String languageTo, boolean isFavourite, Dictionary dictionary, TranslationState translationState) {
        return builder()
                .word(word)
                .translation(translation)
                .languageFrom(languageFrom)
                .languageTo(languageTo)
                .isFavourite(isFavourite)
                .dictionary(dictionary)
                .translationState(translationState)
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

    @Override public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= this.word().hashCode();
        h *= 1000003;
        h ^= this.translation().hashCode();
        h *= 1000003;
        h ^= this.languageFrom().hashCode();
        h *= 1000003;
        h ^= this.languageTo().hashCode();
        h *= 1000003;
        return h;
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

        public abstract Builder dictionary(Dictionary dictionary);

        public abstract Builder translationState(TranslationState translationState);

        public abstract Word build();
    }

    public static TypeAdapter<Word> typeAdapter(Gson gson) {
        return new AutoValue_Word.GsonTypeAdapter(gson);
    }
}
