package org.berendeev.roma.yandexmobilization2017.di;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Component(modules = MainModule.class)
@Singleton
public interface MainComponent {
    Retrofit provideRetrofit();
}
