package org.berendeev.roma.yandexmobilization2017.presentation.presenter;

import org.berendeev.roma.yandexmobilization2017.BuildConfig;
import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetLanguagesInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.Interactor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.SetDirectionFromInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.SetDirectionToInteractor;
import org.berendeev.roma.yandexmobilization2017.presentation.view.LanguageSelectorView;
import org.berendeev.roma.yandexmobilization2017.presentation.view.LanguageSelectorView.Router;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

import static org.berendeev.roma.yandexmobilization2017.presentation.view.DummyView.DUMMY_VIEW;

public class LanguageSelectorPresenter {

    private int titleId;
    private LanguageSelectorView view;

    @Inject SetDirectionFromInteractor setDirectionFromInteractor;
    @Inject SetDirectionToInteractor setDirectionToInteractor;
    @Inject GetLanguagesInteractor getLanguagesInteractor;
    private Interactor<Void, String> interactor;
    private Router router;
    private final CompositeDisposable disposable;

    @Inject
    public LanguageSelectorPresenter() {
        disposable = new CompositeDisposable();
    }

    public void start(){
        disposable.add(getLanguagesInteractor.execute(new LanguageObserver(), Locale.getDefault()));
        view.setTitleById(titleId);
    }

    public void stop(){
        disposable.clear();
        view = DUMMY_VIEW;
    }

    public void onLanguageSelected(String key) {
        interactor.execute(null, key);
        router.moveToTranslator();
    }

    public void setType(int type) {
        if (type == -1 && BuildConfig.DEBUG){
            throw new IllegalArgumentException("wrong type");
        }

        if (type == R.id.language_from_type){
            titleId = R.string.title_language_selector_source;
            interactor = setDirectionFromInteractor;
        }
        if (type == R.id.language_to_type){
            titleId = R.string.title_language_selector_target;
            interactor = setDirectionToInteractor;
        }
    }

    public void setView(LanguageSelectorView view) {
        this.view = view;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    private class LanguageObserver extends DisposableObserver<LanguageMap>{
        @Override public void onNext(LanguageMap map) {
            view.showLanguages(map);
        }

        @Override public void onError(Throwable e) {

        }

        @Override public void onComplete() {

        }
    }
}
