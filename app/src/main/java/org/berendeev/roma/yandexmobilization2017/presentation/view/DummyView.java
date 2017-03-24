package org.berendeev.roma.yandexmobilization2017.presentation.view;

import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import io.reactivex.Observable;

public class DummyView implements LanguageSelectorView, TranslatorView, WordListView {

    public static DummyView DUMMY_VIEW = new DummyView();

    private DummyView(){}

    @Override public void showLanguages(LanguageMap map) {

    }

    @Override public void setTitleById(int titleId) {

    }

    @Override public void setText(Word word) {

    }

    @Override public void setTranslateDirection(TranslateDirection directionFrom, TranslateDirection directionTo) {

    }

    @Override public Observable<String> getTextObservable() {
        return Observable.empty();
    }
}
