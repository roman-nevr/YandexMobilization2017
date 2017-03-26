package org.berendeev.roma.yandexmobilization2017.presentation.view;

import com.jakewharton.rxbinding2.widget.TextViewEditorActionEvent;

import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

import io.reactivex.Observable;

public class DummyView implements LanguageSelectorView, TranslatorView, WordListView {

    public static DummyView DUMMY_VIEW = new DummyView();

    private DummyView(){}

    @Override public void showLanguages(LanguageMap map) {

    }

    @Override public void setTitleById(int titleId) {

    }

    @Override public void switchOffFavButtonAt(int index) {

    }

    @Override public void switchOnFavButtonAt(int index) {

    }

    @Override public void setPreviousWord(Word word) {

    }

    @Override public void setTranslation(Word word) {

    }

    @Override public void setTranslateDirection(TranslateDirection directionFrom, TranslateDirection directionTo) {

    }

    @Override public Observable<String> getTextObservable() {
        return Observable.empty();
    }

    @Override public Observable<Integer> getTextInputDoneObservable() {
        return null;
    }

    @Override public void switchOnFavButton() {

    }

    @Override public void switchOffFavButton() {

    }

    @Override public void showList(List<Word> wordList) {

    }
}
