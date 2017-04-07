package org.berendeev.roma.yandexmobilization2017.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.presentation.App;
import org.berendeev.roma.yandexmobilization2017.presentation.Utils;
import org.berendeev.roma.yandexmobilization2017.presentation.activity.LanguageSelectorActivity;
import org.berendeev.roma.yandexmobilization2017.presentation.adapter.DictionaryAdapter;
import org.berendeev.roma.yandexmobilization2017.presentation.presenter.TranslatorPresenter;
import org.berendeev.roma.yandexmobilization2017.presentation.view.KeyImeChangeListener;
import org.berendeev.roma.yandexmobilization2017.presentation.view.SoftEditText;
import org.berendeev.roma.yandexmobilization2017.presentation.view.TranslatorView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import okhttp3.Response;

import static android.view.KeyEvent.KEYCODE_BACK;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;


public class TranslatorFragment extends Fragment implements TranslatorView, TranslatorView.Router{
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.word_to_translate) SoftEditText wordToTranslate;
    @BindView(R.id.translation) TextView translation;
    @BindView(R.id.fav_button) ImageButton favButton;
    @BindView(R.id.language_from) Button btnLanguageFrom;
    @BindView(R.id.language_to) Button btnLanguageTo;
    @BindView(R.id.swap_button) ImageButton swapButton;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.translation_layout) ConstraintLayout translationLayout;
    @BindView(R.id.error_layout) ConstraintLayout errorLayout;
    @BindView(R.id.repeat_button) Button repeatButton;
    @BindView(R.id.delete_text_button) ImageButton deleteTextButton;

    @Inject TranslatorPresenter presenter;
    private int colorFavourite;
    private int colorNotFavourite;
    private DictionaryAdapter adapter;

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
        presenter.init();
    }

    private void initEditor() {
        wordToTranslate.setHorizontallyScrolling(false);
        wordToTranslate.setLines(3);
        deleteTextButton.setOnClickListener(v -> {
            presenter.onDeleteTextButtonClick();
        });
    }

    private void initErrorUi() {
        repeatButton.setOnClickListener(v -> {
            presenter.onRepeat();
        });
    }

    private void initDictionary() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.getItemAnimator().setChangeDuration(0);
        recyclerView.getItemAnimator().setAddDuration(0);
        recyclerView.getItemAnimator().setRemoveDuration(0);
        recyclerView.getItemAnimator().setMoveDuration(0);
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
        setTextToTranslate(word.word());
        setTranslation(word);
    }

    @Override public void setTranslation(Word word) {
        if(!word.translation().equals(translation.getText().toString())){
            translation.setText(word.translation());
        }
        setFavouritesLabel(word.isFavourite());
        hideConnectionError();
    }

    @Override public void setTranslateDirection(TranslateDirection directionFrom, TranslateDirection directionTo) {
        btnLanguageFrom.setText(directionFrom.name());
        btnLanguageTo.setText(directionTo.name());
    }

    private void setFavouritesLabel(boolean isFavourite) {
        favButton.setColorFilter(isFavourite ? colorFavourite : colorNotFavourite);
    }

    private void setTextToTranslate(String text){
        if(!text.equals(wordToTranslate.getText().toString())){
            wordToTranslate.setText(text);
            wordToTranslate.setSelection(text.length());
        }

    }

    @Override public Observable<String> getTextObservable() {
//        return RxTextView.textChanges(wordToTranslate)
//                .map(charSequence -> charSequence.toString());
        return Observable.create(emitter -> {
            wordToTranslate.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(wordToTranslate.hasFocus()){
                        emitter.onNext(s.toString());
                    }
                }

                @Override public void afterTextChanged(Editable s) {
                }
            });
        });
    }

    private void initEditorActionListener(){
        wordToTranslate.setKeyImeChangeListener(new KeyImeChangeListener() {
            @Override public void onKeyPreIme(int keyCode, KeyEvent event) {
                if(keyCode == KEYCODE_BACK){
                    presenter.onInputDone();
                    wordToTranslate.clearFocus();
                }
            }
        });
        wordToTranslate.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == IME_ACTION_DONE){
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

    @Override public void showDictionary(Dictionary dictionary) {
        if(adapter == null){
            adapter = new DictionaryAdapter(dictionary);
            recyclerView.setAdapter(adapter);
        }else {
            adapter.changeItems(dictionary);
        }
    }

    @Override public void showConnectionError() {
        errorLayout.setVisibility(View.VISIBLE);
        translationLayout.setVisibility(View.GONE);
    }

    @Override public void hideConnectionError() {
        errorLayout.setVisibility(View.GONE);
        translationLayout.setVisibility(View.VISIBLE);
    }

    @Override public void hideImageButtons() {
        deleteTextButton.setVisibility(View.GONE);
        favButton.setVisibility(View.GONE);
    }

    @Override public void showImageButtons() {
        deleteTextButton.setVisibility(View.VISIBLE);
        favButton.setVisibility(View.VISIBLE);
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

}
