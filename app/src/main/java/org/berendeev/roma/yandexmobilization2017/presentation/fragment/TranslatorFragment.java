package org.berendeev.roma.yandexmobilization2017.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.presentation.App;
import org.berendeev.roma.yandexmobilization2017.presentation.Utils;
import org.berendeev.roma.yandexmobilization2017.presentation.activity.LanguageSelectorActivity;
import org.berendeev.roma.yandexmobilization2017.presentation.presenter.TranslatorPresenter;
import org.berendeev.roma.yandexmobilization2017.presentation.view.TranslatorView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;


public class TranslatorFragment extends Fragment implements TranslatorView, TranslatorView.Router{
    public static final int MIN_KEYBOARD_HEIGHT = 200;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.word_to_translate) EditText wordToTranslate;
    @BindView(R.id.translation) TextView translation;
    @BindView(R.id.fav_button) ImageButton favButton;
    @BindView(R.id.language_from) Button btnLanguageFrom;
    @BindView(R.id.language_to) Button btnLanguageTo;
    @BindView(R.id.swap_button) ImageButton swapButton;

    private boolean flag;

    @Inject TranslatorPresenter presenter;
    private int colorFavourite;
    private int colorNotFavourite;
    private View mainView;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.translator, container, false);
        ButterKnife.bind(this, view);
        initUI();
        mainView = view.findViewById(R.id.main_layout);
        return view;
    }

    private void initUI() {
        initHeaderView();
        initFavouritesMarker();
        presenter.init();
    }

    private void initHeaderView(){
        btnLanguageTo.setOnClickListener(v -> {
            presenter.onTargetButtonClick();
        });
        btnLanguageFrom.setOnClickListener(v -> {
            presenter.onSourceButtonClick();
        });
        swapButton.setOnClickListener(v -> {
            presenter.onSwapButtonClick();
        });
    }

    private void initFavouritesMarker(){
        colorFavourite = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        colorNotFavourite = ContextCompat.getColor(getContext(), R.color.grey);
        favButton.setOnClickListener(v -> {
            presenter.onFavButtonClick();
        });
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDI();
    }

    private void initDI() {
        App.getApplication().getMainComponent().plusTranslatorComponent().inject(this);
    }

    @Override public void onStart() {
        super.onStart();
        presenter.setView(this);
        presenter.setRouter(this);
        presenter.start();
    }

    @Override public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override public void setPreviousWord(Word word) {
        setText(word.word());
        translation.setText(word.translation());
        setFavouritesLabel(word.isFavourite());
    }

    @Override public void setTranslation(Word word) {
        translation.setText(word.translation());
        setFavouritesLabel(word.isFavourite());
    }

    @Override public void setTranslateDirection(TranslateDirection directionFrom, TranslateDirection directionTo) {
        btnLanguageFrom.setText(directionFrom.name());

        btnLanguageTo.setText(directionTo.name());
    }

    private void setFavouritesLabel(boolean isFavourite) {
        flag = isFavourite;
        favButton.setColorFilter(flag ? colorFavourite : colorNotFavourite);
    }

    private void setText(String text){
        wordToTranslate.setText(text);
        wordToTranslate.setSelection(text.length());
    }

    @Override public Observable<String> getTextObservable() {
        return RxTextView.textChanges(wordToTranslate)
                .map(charSequence -> charSequence.toString());
    }

    @Override public Observable<Integer> getTextInputDoneObservable(){
        return Observable.create(emitter -> {
            wordToTranslate.setOnEditorActionListener((v, actionId, event) -> {
                if(actionId == IME_ACTION_DONE){
                    hideKeyboard();
                    if (!emitter.isDisposed()){
                        emitter.onNext(R.id.input_done_id);
                    }
                }
                return true;
            });
            mainView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
                int rootViewHeight = mainView.getRootView().getHeight();
                int mainViewHeight = mainView.getHeight();
                if(fromPixelToDp(rootViewHeight - mainViewHeight) >= MIN_KEYBOARD_HEIGHT){
                    emitter.onNext(R.id.input_done_id);
                }
            });
        });
    }

    @Override public void switchOnFavButton() {
        Utils.animateImageButtonColor(favButton, colorNotFavourite, colorFavourite);
    }

    @Override public void switchOffFavButton() {
        Utils.animateImageButtonColor(favButton, colorFavourite, colorNotFavourite);
    }


    @Override public void showSourceLanguageSelector() {
        LanguageSelectorActivity.start(this.getActivity(), R.id.language_from_type);

    }

    @Override public void showTargetLanguageSelector() {
        LanguageSelectorActivity.start(this.getActivity(), R.id.language_to_type);
    }

    @Override public void onHiddenChanged(boolean hidden) {
        if(!hidden){
            presenter.onShow();
        }
    }

    private int fromPixelToDp(int pixels){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return pixels * DisplayMetrics.DENSITY_DEFAULT / metrics.densityDpi;
    }

    private void hideKeyboard() {
        Utils.hideKeyboard(getContext(), wordToTranslate);
    }
}
