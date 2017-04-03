package org.berendeev.roma.yandexmobilization2017.presentation.presenter;

import android.util.Pair;

import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Definition;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.domain.exception.TranslationException;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.CheckIfFavouriteInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetDictionaryInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetLastWordInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetTranslateDirectionInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.RemoveFromFavouritesInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.SaveInFavouriteInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.SaveInHistoryInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.SaveLastWordInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.SwapDirectionsInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.TranslateTextInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.VoidObserver;
import org.berendeev.roma.yandexmobilization2017.presentation.view.DummyView;
import org.berendeev.roma.yandexmobilization2017.presentation.view.TranslatorView;
import org.berendeev.roma.yandexmobilization2017.presentation.view.TranslatorView.Router;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
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
    @Inject SaveInFavouriteInteractor saveInFavouriteInteractor;
    @Inject RemoveFromFavouritesInteractor removeFromFavouritesInteractor;
    @Inject SaveInHistoryInteractor saveInHistoryInteractor;
    @Inject CheckIfFavouriteInteractor checkIfFavouriteInteractor;
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
        showLastWord();
    }

    public void start() {
        disposable.add(getTranslateDirectionInteractor.execute(new DirectionsObserver(), Locale.getDefault()));
        disposable.add(view.getTextObservable()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(text -> {
                    getDictionaryInteractor.execute(new DictionaryObserver(), buildQuery(text));
                    translateTextInteractor.execute(new TranslationObserver(), buildQuery(text));
                    saveLastWordInteractor.execute(new VoidObserver(), Word.builder()
                            .word(text)
                            .translation("")
                            .isFavourite(false)
                            .languageFrom("")
                            .languageTo("")
                            .build());
                    lastWord = lastWord.toBuilder()
                            .word(text)
                            .translation("")
                            .build();
                }));
    }

    public void stop() {
        disposable.clear();
        view = DummyView.DUMMY_VIEW;
    }

    private void saveLastWord() {
        saveLastWordInteractor
                .getObservable(lastWord)
                .subscribeWith(new VoidObserver());
    }

    private void saveLastWordInHistory() {
        saveInHistoryInteractor.execute(new VoidObserver(), lastWord);
    }

    private void translateAndSaveLastWord() {
        translateTextInteractor.execute(new InputDoneObserver(), buildQuery(lastWord.word()));
    }

    private void showLastWord() {
        getLastWordInteractor.execute(new LastWordObserver(), null);
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
        swapDirectionsInteractor.execute(new SwapDirectionsObserver(), null);
    }

    public void onFavButtonClick() {
        if (lastWord.isFavourite()) {
            lastWord = lastWord.toBuilder()
                    .isFavourite(false)
                    .build();
            view.switchOffFavButton();
            removeFromFavouritesInteractor.execute(new VoidObserver(), lastWord);
        } else {
            lastWord = lastWord.toBuilder()
                    .isFavourite(true)
                    .build();
            view.switchOnFavButton();
            saveInFavouriteInteractor.execute(lastWord);
        }
    }

    public void onShow() {
        showLastWord();
    }

    public void onInputDone() {
        translateAndSaveLastWord();
    }

    public void pause() {
        saveLastWord();
    }

    public void onRepeat() {
        disposable.clear();
        start();
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
            view.setTranslation(word);
            lastWord = word;
            view.hideConnectionError();
        }

        @Override public void onError(Throwable e) {
            if(e instanceof TranslationException){
                view.showTranslationError();
            }
            view.showConnectionError();
            Timber.d(e, ERROR);
        }

        @Override public void onComplete() {
        }

    }

    private class LastWordObserver extends DisposableObserver<Word> {

        @Override public void onNext(Word word) {
            view.setPreviousWord(word);
            lastWord = word;
            langFrom = word.languageFrom();
            langTo = word.languageTo();
        }

        @Override public void onError(Throwable e) {

        }

        @Override public void onComplete() {
            dispose();
        }

    }

    private TranslationQuery buildQuery(String text) {
        return TranslationQuery.create(text, langFrom, langTo);
    }

    private class InputDoneObserver extends DisposableObserver<Word> {

        @Override public void onNext(Word word) {
            view.setTranslation(word);
            lastWord = word;
            saveLastWordInHistory();
        }

        @Override public void onError(Throwable e) {
        }

        @Override public void onComplete() {
            dispose();
        }
    }

    private class SwapDirectionsObserver extends DisposableObserver<Void> {

        @Override public void onNext(Void aVoid) {
        }

        @Override public void onError(Throwable e) {

        }

        @Override public void onComplete() {
            view.setPreviousWord(lastWord.toBuilder()
                    .word(lastWord.translation())
                    .translation(lastWord.word())
                    .build());
            dispose();
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
