package org.berendeev.roma.yandexmobilization2017.presentation.presenter;

import android.util.Pair;

import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetTranslateDirectionInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.SwapDirectionsInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.VoidObserver;
import org.berendeev.roma.yandexmobilization2017.presentation.fragment.TranslatorFragment;
import org.berendeev.roma.yandexmobilization2017.presentation.view.TranslatorView;
import org.berendeev.roma.yandexmobilization2017.presentation.view.TranslatorView.Router;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class TranslatorPresenter {

    private TranslatorView view;

    @Inject GetTranslateDirectionInteractor getTranslateDirectionInteractor;
    @Inject SwapDirectionsInteractor swapDirectionsInteractor;
    private Router router;

    @Inject
    public TranslatorPresenter() {}

    public void start(){
        getTranslateDirectionInteractor.execute(new DirectionsObserver(), Locale.getDefault());
    }

    public void stop(){
        getTranslateDirectionInteractor.dispose();
    }

    public void setView(TranslatorView view) {
        this.view = view;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public void onTargetButtonClick() {
        router.showTargetLanguageSelector();
    }

    public void onSourceButtonClick() {
        router.showSourceLanguageSelector();
    }

    public void onSwapButtonClick() {
        swapDirectionsInteractor.execute(new VoidObserver(), null);
    }

    private class DirectionsObserver extends DisposableObserver<Pair<TranslateDirection, TranslateDirection>>{
        @Override public void onNext(Pair<TranslateDirection, TranslateDirection> pair) {
            view.setTranslateDirection(pair.first, pair.second);
        }

        @Override public void onError(Throwable e) {

        }

        @Override public void onComplete() {

        }
    }
}
