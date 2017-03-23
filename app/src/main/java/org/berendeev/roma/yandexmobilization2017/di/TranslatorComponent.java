package org.berendeev.roma.yandexmobilization2017.di;

import org.berendeev.roma.yandexmobilization2017.presentation.fragment.TranslatorFragment;

import dagger.Subcomponent;

@Subcomponent
public interface TranslatorComponent {
    void inject(TranslatorFragment fragment);
}
