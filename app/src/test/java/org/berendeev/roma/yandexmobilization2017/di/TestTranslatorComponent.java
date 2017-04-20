package org.berendeev.roma.yandexmobilization2017.di;

import org.berendeev.roma.yandexmobilization2017.InteractorTest;

import dagger.Subcomponent;

@Subcomponent
public interface TestTranslatorComponent {
    void inject(InteractorTest test);
}
