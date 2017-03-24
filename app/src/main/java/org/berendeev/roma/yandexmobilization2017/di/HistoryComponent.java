package org.berendeev.roma.yandexmobilization2017.di;

import org.berendeev.roma.yandexmobilization2017.presentation.fragment.HistoryFragment;

import dagger.Subcomponent;

@Subcomponent
public interface HistoryComponent {
    void inject(HistoryFragment fragment);
}
