package org.berendeev.roma.yandexmobilization2017.presentation.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.presentation.App;
import org.berendeev.roma.yandexmobilization2017.presentation.Utils;
import org.berendeev.roma.yandexmobilization2017.presentation.activity.LanguageSelectorActivity;
import org.berendeev.roma.yandexmobilization2017.presentation.adapter.DictionaryViewWrapper;
import org.berendeev.roma.yandexmobilization2017.presentation.presenter.TranslatorPresenter;
import org.berendeev.roma.yandexmobilization2017.presentation.view.SoftEditText;
import org.berendeev.roma.yandexmobilization2017.presentation.view.TranslatorView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

import static android.view.KeyEvent.KEYCODE_BACK;
import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;


public class TranslatorFragment extends Fragment implements TranslatorView, TranslatorView.Router {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.word_to_translate) SoftEditText wordToTranslate;
    @BindView(R.id.translation) TextView translation;
    @BindView(R.id.fav_button) ImageButton favButton;
    @BindView(R.id.language_from) Button btnLanguageFrom;
    @BindView(R.id.language_to) Button btnLanguageTo;
    @BindView(R.id.swap_button) ImageButton swapButton;
    @BindView(R.id.dictionary) TextView tvDictionary;
    @BindView(R.id.main_layout) ConstraintLayout mainLayout;
    @BindView(R.id.translation_layout) ConstraintLayout translationLayout;
    @BindView(R.id.dictionary_layout) ConstraintLayout dictionaryLayout;
    @BindView(R.id.error_layout) ConstraintLayout errorLayout;
    @BindView(R.id.repeat_button) Button repeatButton;
    @BindView(R.id.delete_text_button) ImageButton deleteTextButton;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.translator_ua) TextView translatorUa;
    @BindView(R.id.dictionary_ua) TextView dictionaryUa;

    @Inject TranslatorPresenter presenter;
    private int colorFavourite;
    private int colorAccent;
    private int colorNotFavourite;
    private DictionaryViewWrapper wrapper;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDI();
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.translator, container, false);
        ButterKnife.bind(this, view);
        initUI();
        return view;
    }

    private void initUI() {
        initHeaderView();
        initFavouritesMarker();
        initEditor();
        initEditorActionListener();
        initDictionary();
        initErrorUi();
        initLinks();
    }

    private void initLinks() {
        translatorUa.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://translate.yandex.ru/"));
            startActivity(browserIntent);
        });
        dictionaryUa.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://api.yandex.ru/dictionary/"));
            startActivity(browserIntent);
        });
    }

    private void initEditor() {
        wordToTranslate.setHorizontallyScrolling(false);
        wordToTranslate.setLines(3);
        deleteTextButton.setOnClickListener(v -> {
            wordToTranslate.requestFocus();
            showKeyboard();
            presenter.onDeleteTextButtonClick();
        });
    }

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(wordToTranslate, InputMethodManager.SHOW_IMPLICIT);
    }

    private void initErrorUi() {
        repeatButton.setOnClickListener(v -> {
            presenter.onRepeat();
        });
    }

    private void initDictionary() {
        int dictionaryHeaderSize = getResources().getDimensionPixelSize(R.dimen.text_regular_size);
        int dictionaryTextSize = getResources().getDimensionPixelSize(R.dimen.text_medium_size);
        int colorText = ContextCompat.getColor(getContext(), R.color.colorText);
        wrapper = new DictionaryViewWrapper(colorAccent, colorText, dictionaryHeaderSize, dictionaryTextSize);
        tvDictionary.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void initHeaderView() {
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

    private void initFavouritesMarker() {
        colorFavourite = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        colorAccent = ContextCompat.getColor(getContext(), R.color.colorAccent);
        colorNotFavourite = ContextCompat.getColor(getContext(), R.color.light_grey);
        favButton.setOnClickListener(v -> {
            presenter.onFavButtonClick();
        });
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

    @Override public void onPause() {
        super.onPause();
        presenter.onInputDone();
    }

    @Override public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override public void setTranslation(Word word) {
        translation.setText(word.translation());
        showDictionary(word.dictionary());

        if (word.word().equals("")){
            hideTranslatorUa();
            favButton.setVisibility(INVISIBLE);
        }else {
            showTranslatorUa();
            favButton.setVisibility(VISIBLE);
        }
        setFavouritesLabel(word.isFavourite());
    }

    @Override public void setTranslateDirection(TranslateDirection directionFrom, TranslateDirection directionTo) {
        btnLanguageFrom.setText(directionFrom.name());
        btnLanguageTo.setText(directionTo.name());
    }

    private void setFavouritesLabel(boolean isFavourite) {
        favButton.setColorFilter(isFavourite ? colorFavourite : colorNotFavourite);
    }

    @Override public void setTextToTranslate(String text) {
        if (!text.equals(wordToTranslate.getText().toString())) {
            wordToTranslate.setText(text);
            wordToTranslate.setSelection(text.length());
        }
        if(text.isEmpty()){
            deleteTextButton.setVisibility(GONE);
        }else {
            deleteTextButton.setVisibility(VISIBLE);
        }

    }

    @Override public Observable<String> getTextObservable() {
        return Observable.create(emitter -> wordToTranslate
                .addTextChangedListener(new TextWatcher() {
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (wordToTranslate.hasFocus()) {
                            emitter.onNext(s.toString());
                        }
                        if(s.length() == 0){
                            deleteTextButton.setVisibility(GONE);
                        }else{
                            deleteTextButton.setVisibility(VISIBLE);
                        }
                    }

                    @Override public void afterTextChanged(Editable s) {
                    }
                }));
    }

    private void initEditorActionListener() {
        mainLayout.setOnTouchListener((v, event) -> {
            if(wordToTranslate.hasFocus()){
                hideKeyboard();
                presenter.onInputDone();
                wordToTranslate.clearFocus();
                return true;
            }else {
                return false;
            }
        });
        tvDictionary.setOnTouchListener((v, event) -> {
            if(wordToTranslate.hasFocus()){
                hideKeyboard();
                presenter.onInputDone();
                wordToTranslate.clearFocus();
                return true;
            }else {
                return false;
            }

        });

        wordToTranslate.setKeyImeChangeListener((keyCode, event) -> {
            if (keyCode == KEYCODE_BACK) {
                presenter.onInputDone();
                wordToTranslate.clearFocus();
            }
        });
        wordToTranslate.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == IME_ACTION_DONE) {
                hideKeyboard();
                presenter.onInputDone();
                wordToTranslate.clearFocus();
            }
            return false;
        });
    }

    @Override public void switchOnFavButton() {
        Utils.animateImageButtonColor(favButton, colorNotFavourite, colorFavourite);
    }

    @Override public void switchOffFavButton() {
        Utils.animateImageButtonColor(favButton, colorFavourite, colorNotFavourite);
    }

    @Override public void showConnectionError() {
        errorLayout.setVisibility(VISIBLE);
    }

    @Override public void hideConnectionError() {
        errorLayout.setVisibility(GONE);
    }

    @Override public void showProgress() {
        progressBar.setVisibility(VISIBLE);
    }

    @Override public void hideProgress() {
        progressBar.setVisibility(GONE);
    }

    @Override public void showTranslation() {
        translationLayout.setVisibility(VISIBLE);
        dictionaryLayout.setVisibility(VISIBLE);
    }

    @Override public void hideTranslation() {
        translationLayout.setVisibility(INVISIBLE);
        dictionaryLayout.setVisibility(INVISIBLE);
    }

    @Override public void showSourceLanguageSelector() {
        LanguageSelectorActivity.start(this.getActivity(), R.id.language_from_type);

    }

    @Override public void showTargetLanguageSelector() {
        LanguageSelectorActivity.start(this.getActivity(), R.id.language_to_type);
    }

    private void hideKeyboard() {
        Utils.hideKeyboard(getContext(), wordToTranslate);
    }

    private void showDictionary(Dictionary dictionary) {
        tvDictionary.setText(wrapper.getText(dictionary));
        if(dictionary.equals(Dictionary.EMPTY)){
            hideDictionaryUa();
        }else {
            showDictionaryUa();
        }
    }

    private void showTranslatorUa(){
        translatorUa.setVisibility(VISIBLE);
    }

    private void hideTranslatorUa(){
        translatorUa.setVisibility(INVISIBLE);
    }

    private void showDictionaryUa(){
        dictionaryUa.setVisibility(VISIBLE);
    }

    private void hideDictionaryUa(){
        dictionaryUa.setVisibility(INVISIBLE);
    }

}
