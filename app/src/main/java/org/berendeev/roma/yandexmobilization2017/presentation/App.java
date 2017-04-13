package org.berendeev.roma.yandexmobilization2017.presentation;

import android.app.Application;

import com.facebook.stetho.Stetho;

import org.berendeev.roma.yandexmobilization2017.BuildConfig;
import org.berendeev.roma.yandexmobilization2017.di.DaggerMainComponent;
import org.berendeev.roma.yandexmobilization2017.di.MainComponent;
import org.berendeev.roma.yandexmobilization2017.di.MainModule;

import timber.log.Timber;


public class App extends Application {

    private MainComponent mainComponent;
    private static App application;

    @Override public void onCreate() {
        super.onCreate();
        initDI();
        initStetho();
        initTimber();
        initInstance();
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

    private void initStetho(){
        if(!BuildConfig.DEBUG){
            return;
        }
        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );

        // Enable command line interface
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        );

        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);
    }

    private void initTimber(){
        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
    }

    private void initInstance(){
        application = this;
    }

}