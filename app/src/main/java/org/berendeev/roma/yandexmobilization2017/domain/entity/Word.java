package org.berendeev.roma.yandexmobilization2017.domain.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Word {
    public abstract String word();
    public abstract String translation();

    public abstract String language();

    public abstract boolean isFavourite();
}
