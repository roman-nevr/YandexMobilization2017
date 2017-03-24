package org.berendeev.roma.yandexmobilization2017.presentation.fragment;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.presentation.App;
import org.berendeev.roma.yandexmobilization2017.presentation.activity.LanguageSelectorActivity;
import org.berendeev.roma.yandexmobilization2017.presentation.presenter.TranslatorPresenter;
import org.berendeev.roma.yandexmobilization2017.presentation.view.TranslatorView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TranslatorFragment extends Fragment implements TranslatorView, TranslatorView.Router{
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.word_to_translate) TextView wordToTranslate;
    @BindView(R.id.translation) TextView translation;
    @BindView(R.id.fav_button) ImageButton favButton;
    @BindView(R.id.language_from) Button btnLanguageFrom;
    @BindView(R.id.language_to) Button btnLanguageTo;
    @BindView(R.id.swap_button) ImageButton swapButton;

    private boolean flag;

    @Inject TranslatorPresenter presenter;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.translator, container, false);
        ButterKnife.bind(this, view);
        initUI();
        return view;
    }

    private void initUI() {
        flag = true;
        btnLanguageTo.setOnClickListener(v -> {
            presenter.onTargetButtonClick();
        });
        btnLanguageFrom.setOnClickListener(v -> {
            presenter.onSourceButtonClick();
        });
        swapButton.setOnClickListener(v -> {
            presenter.onSwapButtonClick();
        });

        int colorPrimary = getResources().getColor(R.color.colorPrimary);
        int colorGrey = getResources().getColor(R.color.grey);
        favButton.setOnClickListener(v -> {

            int colorFrom, colorTo;

            if(flag){
                colorFrom = colorPrimary;
                colorTo = colorGrey;
            }else {
                colorFrom = colorGrey;
                colorTo = colorPrimary;
            }
            flag = !flag;
            ObjectAnimator animator = ObjectAnimator.ofObject(favButton,
                    "colorFilter", new ArgbEvaluator(), colorFrom, colorTo);

            animator.setDuration(500);
            animator.start();
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

    @Override public void setText(Word word) {
        wordToTranslate.setText(word.word());
        translation.setText(word.translation());
    }

    @Override public void setTranslateDirection(TranslateDirection directionFrom, TranslateDirection directionTo) {
        btnLanguageFrom.setText(directionFrom.name());

        btnLanguageTo.setText(directionTo.name());
    }

    @Override public void showSourceLanguageSelector() {
        LanguageSelectorActivity.start(this.getActivity(), R.id.languageFromType);

    }

    @Override public void showTargetLanguageSelector() {
        LanguageSelectorActivity.start(this.getActivity(), R.id.languageToType);
    }
}
