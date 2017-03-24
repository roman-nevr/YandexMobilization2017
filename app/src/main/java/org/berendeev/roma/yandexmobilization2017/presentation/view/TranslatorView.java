package org.berendeev.roma.yandexmobilization2017.presentation.view;

import com.jakewharton.rxbinding2.widget.TextViewEditorActionEvent;

import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import io.reactivex.Observable;

public interface TranslatorView {
    void initTranslatorText(Word word);

    void setTranslation(Word word);

    void setTranslateDirection(TranslateDirection directionFrom, TranslateDirection directionTo);

    Observable<String> getTextObservable();

    Observable<Integer> getTextInputDoneObservable();

    interface Router {
        void showSourceLanguageSelector();

        void showTargetLanguageSelector();
    }
}
