package org.berendeev.roma.yandexmobilization2017.presentation.presenter;

import android.util.Pair;

import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetDictionaryInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetFavouriteStateInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetLastWordInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetTranslateDirectionInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetTranslationInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.RemoveFromFavouritesInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.SaveInFavouriteInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.SaveLastWordInHistoryInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.SaveLastWordInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.SwapDirectionsInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.ToggleFavouriteStateInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.TranslateTextInteractor;
import org.berendeev.roma.yandexmobilization2017.presentation.view.TranslatorView;
import org.berendeev.roma.yandexmobilization2017.presentation.view.TranslatorView.Router;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

import static org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState.connectionError;
import static org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState.ok;

public class TranslatorPresenter {

    private static final String ERROR = "translator_error";
    private TranslatorView view;

    @Inject GetTranslateDirectionInteractor getTranslateDirectionInteractor;
    @Inject SwapDirectionsInteractor swapDirectionsInteractor;
    @Inject TranslateTextInteractor translateTextInteractor;
    @Inject GetLastWordInteractor getLastWordInteractor;
    @Inject RemoveFromFavouritesInteractor removeFromFavouritesInteractor;
    @Inject SaveInFavouriteInteractor saveInFavouriteInteractor;
    @Inject SaveLastWordInHistoryInteractor saveLastWordInHistoryInteractor;
    @Inject GetDictionaryInteractor getDictionaryInteractor;
    @Inject SaveLastWordInteractor saveLastWordInteractor;
    @Inject GetTranslationInteractor getTranslationInteractor;
    @Inject GetFavouriteStateInteractor getFavouriteStateInteractor;
    @Inject ToggleFavouriteStateInteractor toggleFavouriteStateInteractor;
    private Router router;
    private final CompositeDisposable disposable;
    private Word lastWord;

    @Inject
    public TranslatorPresenter() {
        disposable = new CompositeDisposable();
    }

    public void start() {
        subscribeOnTranslateDirections();
        subscribeOnTextInput();
        subscribeOnTranslation();
        subscribeOnFavouriteState();
    }

    private void subscribeOnFavouriteState() {
        disposable.add(getFavouriteStateInteractor.execute(new FavouriteObserver(), null));
    }

    private void subscribeOnTranslation() {
        disposable.add(getTranslationInteractor.execute(new ResultObserver(), null));
    }

    public void stop() {
        disposable.clear();
        router = null;
    }

    private void subscribeOnTextInput() {
        disposable.add(view.getTextObservable()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(text -> translateTextInteractor
                        .execute(null, text)
                ));
    }

    private void subscribeOnTranslateDirections() {
        Disposable disposable = getTranslateDirectionInteractor.execute(new DirectionsObserver(), null);
        this.disposable.add(disposable);
    }

    private void saveLastWordInHistory() {
        saveLastWordInHistoryInteractor.execute(null, null);
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
        swapDirectionsInteractor.execute(null, null);
    }

    public void onFavButtonClick() {
        toggleFavouriteStateInteractor.execute(null, null);

    }

    public void onInputDone() {
        saveLastWordInHistory();
    }

    public void onRepeat() {
        disposable.clear();
        start();
    }

    public void onDeleteTextButtonClick() {
        lastWord = Word.EMPTY;
        view.setPreviousWord(Word.EMPTY);
        view.hideImageButtons();
    }

    private void setImages(String text){
        if (text.equals("")){
            view.hideImageButtons();
        }else {
            view.showImageButtons();
        }
    }

    public void onShow() {
    }

    private class DirectionsObserver extends DisposableObserver<Pair<TranslateDirection, TranslateDirection>> {

        @Override public void onNext(Pair<TranslateDirection, TranslateDirection> pair) {
            view.setTranslateDirection(pair.first, pair.second);
        }

        @Override public void onError(Throwable e) {
            Timber.wtf(e, ERROR);
        }

        @Override public void onComplete() {
        }

    }

    private class ResultObserver extends DisposableObserver<Word> {
        @Override public void onNext(Word word) {
            if(word.translationState() == connectionError){
                view.setPreviousWord(word);
                view.showConnectionError();
            }
            if (word.translationState() == ok){
                view.hideConnectionError();
                setImages(word.word());
                lastWord = word;
                view.setPreviousWord(word);
            }
        }

        @Override public void onError(Throwable e) {
        }

        @Override public void onComplete() {
        }
    }

    private class FavouriteObserver extends DisposableObserver<Boolean> {
        @Override public void onNext(Boolean favourite) {
            if(favourite){
                view.switchOnFavButton();
            }else {
                view.switchOffFavButton();
            }
        }

        @Override public void onError(Throwable e) {

        }

        @Override public void onComplete() {

        }
    }
}
