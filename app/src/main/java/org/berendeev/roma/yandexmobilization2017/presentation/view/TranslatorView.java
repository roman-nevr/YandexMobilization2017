package org.berendeev.roma.yandexmobilization2017.presentation.view;

import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

public interface TranslatorView {
    void setText(Word word);
    void setTranslateDirection(TranslateDirection directionFrom, TranslateDirection directionTo);

    interface Router{
        void showSourceLanguageSelector();
        void showTargetLanguageSelector();
    }
}
