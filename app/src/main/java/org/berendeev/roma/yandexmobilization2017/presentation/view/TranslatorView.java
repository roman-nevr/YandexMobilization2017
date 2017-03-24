package org.berendeev.roma.yandexmobilization2017.presentation.view;

import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import io.reactivex.Observable;

public interface TranslatorView {
    void setText(Word word);
    void setTranslateDirection(TranslateDirection directionFrom, TranslateDirection directionTo);

    Observable<String> getTextObservable();

    interface Router{
        void showSourceLanguageSelector();
        void showTargetLanguageSelector();
    }
}
