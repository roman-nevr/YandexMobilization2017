package org.berendeev.roma.yandexmobilization2017.presentation;

import android.app.Application;

import org.berendeev.roma.yandexmobilization2017.di.DaggerMainComponent;
import org.berendeev.roma.yandexmobilization2017.di.MainComponent;
import org.berendeev.roma.yandexmobilization2017.di.MainModule;


public class App extends Application {

    private MainComponent mainComponent;
    private static App application;

    @Override public void onCreate() {
        super.onCreate();
        initDI();
        application = this;
    }

    private void initDI() {
        mainComponent = DaggerMainComponent.builder().mainModule(new MainModule(getApplicationContext())).build();
    }

    public MainComponent getMainComponent(){
        return mainComponent;
    }

    public static App getApplication(){
        return application;
    }

}
