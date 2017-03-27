package org.berendeev.roma.yandexmobilization2017.presentation.presenter;

import android.util.Pair;

import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.CheckIfFavouriteInteractor;
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

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class TranslatorPresenter {

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
    private Router router;
    private final CompositeDisposable disposable;
    private String langFrom, langTo;
    private Word lastWord;

    @Inject
    public TranslatorPresenter() {
        disposable = new CompositeDisposable();
    }

    public void init() {
        getLastWordInteractor.execute(new LastWordObserver(), null);
    }

    public void start() {
        disposable.add(getTranslateDirectionInteractor.execute(new DirectionsObserver(), Locale.getDefault()));
        disposable.add(view.getTextObservable()
                .distinctUntilChanged()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(text -> {
                    translateTextInteractor.execute(new TranslationObserver(), buildQuery(text));
                }));
        view.getTextInputDoneObservable()
                .filter(integer -> integer == R.id.input_done_id)
                .subscribe(integer -> {
                    translateAndSaveLastWord();
                });
    }

    public void stop() {
        disposable.clear();
        view = DummyView.DUMMY_VIEW;
        saveLastWord();
    }

    private void saveLastWord(){
        saveLastWordInteractor.execute(new VoidObserver(), lastWord);
    }

    private void saveLastWordInHistory(){
        saveInHistoryInteractor.execute(new VoidObserver(), lastWord);
    }

    private void translateAndSaveLastWord(){
        translateTextInteractor.execute(new InputDoneObserver(), buildQuery(lastWord.word()));
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

    public void onFavButtonClick() {
        if (lastWord.isFavourite()) {
            lastWord = lastWord.toBuilder()
                    .isFavourite(false)
                    .build();
            view.switchOffFavButton();
            removeFromFavouritesInteractor.execute(new VoidObserver(), lastWord);
        }else {
            lastWord = lastWord.toBuilder()
                    .isFavourite(true)
                    .build();
            view.switchOnFavButton();
            saveInFavouriteInteractor.execute(lastWord);
        }
    }

    public void onShow() {
        checkIfFavouriteInteractor.execute(new TranslationObserver(), lastWord);
    }

    private class DirectionsObserver extends DisposableObserver<Pair<TranslateDirection, TranslateDirection>> {

        @Override public void onNext(Pair<TranslateDirection, TranslateDirection> pair) {
            langFrom = pair.first.key();
            langTo = pair.second.key();
            view.setTranslateDirection(pair.first, pair.second);
            translateAndSaveLastWord();
        }

        @Override public void onError(Throwable e) {
        }

        @Override public void onComplete() {
        }

    }

    private class TranslationObserver extends DisposableObserver<Word> {

        @Override public void onNext(Word word) {
            view.setTranslation(word);
            lastWord = word;
        }

        @Override public void onError(Throwable e) {
        }

        @Override public void onComplete() {
            dispose();
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

    private class InputDoneObserver extends DisposableObserver<Word>{

        @Override public void onNext(Word word) {
            view.setTranslation(word);
            lastWord = word;
        }

        @Override public void onError(Throwable e) {
        }

        @Override public void onComplete() {
            saveLastWordInHistory();
            saveLastWord();
            dispose();
        }
    }
}
