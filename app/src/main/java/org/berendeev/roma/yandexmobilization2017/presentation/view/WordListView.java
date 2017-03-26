package org.berendeev.roma.yandexmobilization2017.presentation.view;

import android.support.annotation.StringRes;

import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

public interface WordListView {
    void showList(List<Word> wordList);

    void setTitleById(@StringRes int id);

    void switchOffFavButtonAt(int index);

    void switchOnFavButtonAt(int index);
}
