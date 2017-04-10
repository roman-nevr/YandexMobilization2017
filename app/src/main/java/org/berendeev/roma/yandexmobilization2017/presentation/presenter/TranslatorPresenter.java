package org.berendeev.roma.yandexmobilization2017.presentation.presenter;

import android.util.Pair;

import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetDictionaryInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetLastWordInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetTranslateDirectionInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.RemoveFromFavouritesInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.SaveInFavouriteInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.SaveLastWordInHistoryInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.SwapDirectionsInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.TranslateTextInteractor;
import org.berendeev.roma.yandexmobilization2017.presentation.view.DummyView;
import org.berendeev.roma.yandexmobilization2017.presentation.view.TranslatorView;
import org.berendeev.roma.yandexmobilization2017.presentation.view.TranslatorView.Router;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

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
    private Router router;
    private final CompositeDisposable disposable;
    private Word lastWord;

    @Inject
    public TranslatorPresenter() {
        disposable = new CompositeDisposable();
    }

    public void start() {
        loadLastWord();
        subscribeOnTranslateDirections();
        subscribeOnLastWord();
        subscribeOnTextInput();
    }

    public void stop() {
        disposable.clear();
        //view = DummyView.DUMMY_VIEW;   //даже если какой-то запрос зависнет и ответ придет после остановки активити, то ничего не упадет
        router = null;
    }

    private void loadLastWord() {
        getLastWordInteractor
                .execute(null)
                .firstElement()
                .subscribe(word -> view.setPreviousWord(word));
    }

    private void subscribeOnTextInput() {
        disposable.add(view.getTextObservable()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(text -> {
                    disposable.add(getDictionaryInteractor.execute(new DictionaryObserver(), text));
                    disposable.add(translateTextInteractor.execute(new TranslationObserver(), text));
                }));
    }

    private void subscribeOnTranslateDirections() {
        Disposable disposable = getTranslateDirectionInteractor.execute(new DirectionsObserver(), Locale.getDefault());
        this.disposable.add(disposable);
    }

    private void subscribeOnLastWord() {
        disposable.add(getLastWordInteractor.execute(new LastWordObserver(), null));
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
        if (lastWord.isFavourite()) {
            view.switchOffFavButton();
            removeFromFavouritesInteractor.execute(null, lastWord);
        } else {
            view.switchOnFavButton();
            saveInFavouriteInteractor.execute(null, lastWord);
        }
    }

    public void onInputDone() {
        saveLastWordInHistory();
    }

    public void onRepeat() {
        disposable.clear();
        start();
    }

    public void onDeleteTextButtonClick() {
        view.setPreviousWord(Word.EMPTY);
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
            view.hideConnectionError();
        }

        @Override public void onError(Throwable e) {
            view.showConnectionError();
            Timber.d(e, ERROR);
        }

        @Override public void onComplete() {
        }

    }

    private class TranslationObserver extends DisposableObserver<Word> {

        @Override public void onNext(Word word) {
            setImages(word.word());
            view.setTranslation(word);
            lastWord = word;
            view.hideConnectionError();
        }

        @Override public void onError(Throwable e) {
            view.showConnectionError();
            Timber.d(e, ERROR);
        }

        @Override public void onComplete() {
        }

    }

    private class LastWordObserver extends DisposableObserver<Word> {

        @Override public void onNext(Word word) {
            setImages(word.word());
            view.setPreviousWord(word);
            lastWord = word;
        }

        @Override public void onError(Throwable e) {
        }

        @Override public void onComplete() {
        }

    }

    private class DictionaryObserver extends DisposableObserver<Dictionary> {

        @Override public void onNext(Dictionary dictionary) {
            view.showDictionary(dictionary);
            view.hideConnectionError();
        }
        @Override public void onError(Throwable e) {
            view.showConnectionError();
            Timber.d(e, ERROR);
        }

        @Override public void onComplete() {
        }
    }
}
