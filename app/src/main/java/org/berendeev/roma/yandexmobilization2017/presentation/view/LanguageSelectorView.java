package org.berendeev.roma.yandexmobilization2017.presentation.view;

import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;

public interface LanguageSelectorView {
    void showLanguages(LanguageMap map);
    void setTitleById(int titleId);

    interface Router{
        public void moveToTranslator();
    }
}
