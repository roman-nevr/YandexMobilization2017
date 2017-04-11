package org.berendeev.roma.yandexmobilization2017.presentation.view;

import com.jakewharton.rxbinding2.widget.TextViewEditorActionEvent;

import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import io.reactivex.Observable;

public interface TranslatorView {
    void setPreviousWord(Word word);

    void setTranslation(Word word);

    void setTranslateDirection(TranslateDirection directionFrom, TranslateDirection directionTo);

    Observable<String> getTextObservable();

    void switchOnFavButton();

    void switchOffFavButton();

    void showConnectionError();

    void hideConnectionError();

    void hideImageButtons();

    void showImageButtons();

    void showLanguagesLoadError();

    void hideLanguagesLoadError();

    interface Router {
        void showSourceLanguageSelector();

        void showTargetLanguageSelector();
    }
}
