package org.berendeev.roma.yandexmobilization2017.di;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = MainModule.class)
@Singleton
public interface MainComponent {

    void inject(TestActivity activity);

    LanguageSelectorComponent plusLanguageSelectorComponent();

    TranslatorComponent plusTranslatorComponent();

    HistoryComponent plusHistoryComponent();

    FavouritesComponent plusFavouritesComponent();
}
