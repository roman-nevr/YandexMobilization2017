package org.berendeev.roma.yandexmobilization2017.di;

import org.berendeev.roma.yandexmobilization2017.presentation.fragment.HistoryFragment;

import dagger.Subcomponent;

@ListScope
@Subcomponent(modules = HistoryAndFavouritesModule.class)
public interface HistoryAndFavouritesComponent {
    void inject(HistoryFragment fragment);
}
