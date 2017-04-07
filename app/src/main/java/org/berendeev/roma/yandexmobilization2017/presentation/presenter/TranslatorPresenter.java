package org.berendeev.roma.yandexmobilization2017.presentation.presenter;

import android.util.Pair;

import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetDictionaryInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetLastWordInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetTranslateDirectionInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.RemoveFromFavouritesInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.ToggleIsFavouriteInLastWordInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.SaveLastWordInHistoryInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.SaveLastWordInteractor;
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

    public static final String ERROR = "translator_error";
    private TranslatorView view;

    @Inject GetTranslateDirectionInteractor getTranslateDirectionInteractor;
    @Inject SwapDirectionsInteractor swapDirectionsInteractor;
    @Inject TranslateTextInteractor translateTextInteractor;
    @Inject SaveLastWordInteractor saveLastWordInteractor;
    @Inject GetLastWordInteractor getLastWordInteractor;
    @Inject ToggleIsFavouriteInLastWordInteractor toggleIsFavouriteInLastWordInteractor;
    @Inject RemoveFromFavouritesInteractor removeFromFavouritesInteractor;
    @Inject SaveLastWordInHistoryInteractor saveLastWordInHistoryInteractor;
    @Inject GetDictionaryInteractor getDictionaryInteractor;
    private Router router;
    private final CompositeDisposable disposable;
    private String langFrom, langTo;
    private Word lastWord;

    @Inject
    public TranslatorPresenter() {
        disposable = new CompositeDisposable();
    }

    public void init() {
    }

    public void start() {
        subscribeOnTranslateDirections();
        subscribeOnLastWord();
        subscribeOnTextInput();
    }

    public void stop() {
        disposable.clear();
        view = DummyView.DUMMY_VIEW;   //даже если какой-то запрос зависнет и ответ придет после остановки активити, то ничего не упадет
        router = null;
    }


    private void subscribeOnTextInput() {
        disposable.add(view.getTextObservable()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(text -> {
                    disposable.add(getDictionaryInteractor.execute(new DictionaryObserver(), buildQuery(text)));
                    disposable.add(translateTextInteractor.execute(new TranslationObserver(), buildQuery(text)));
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
        } else {
            view.switchOnFavButton();
        }
        toggleIsFavouriteInLastWordInteractor.execute(null, null);
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

    private class DirectionsObserver extends DisposableObserver<Pair<TranslateDirection, TranslateDirection>> {

        @Override public void onNext(Pair<TranslateDirection, TranslateDirection> pair) {
            langFrom = pair.first.key();
            langTo = pair.second.key();
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
            langFrom = word.languageFrom();
            langTo = word.languageTo();
        }

        @Override public void onError(Throwable e) {
        }

        @Override public void onComplete() {
        }

    }

    private TranslationQuery buildQuery(String text) {
        return TranslationQuery.create(text, langFrom, langTo);
    }

    private class SwapDirectionsObserver extends DisposableObserver<Void> {

        @Override public void onNext(Void aVoid) {
        }

        @Override public void onError(Throwable e) {

        }

        @Override public void onComplete() {
//            setImages(lastWord.word());
//            view.setPreviousWord(lastWord.toBuilder()
//                    .word(lastWord.translation())
//                    .translation(lastWord.word())
//                    .build());
//            dispose();
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
