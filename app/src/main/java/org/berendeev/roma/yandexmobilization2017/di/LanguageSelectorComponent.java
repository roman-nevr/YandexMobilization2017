package org.berendeev.roma.yandexmobilization2017.di;

import org.berendeev.roma.yandexmobilization2017.presentation.activity.LanguageSelectorActivity;

import dagger.Subcomponent;

@Subcomponent
public interface LanguageSelectorComponent {
    void inject(LanguageSelectorActivity activity);
}
