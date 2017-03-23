package org.berendeev.roma.yandexmobilization2017.presentation.activity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.data.http.TranslateAPI;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.presentation.App;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static org.berendeev.roma.yandexmobilization2017.Consts.API_KEY;


public class TestActivity extends AppCompatActivity {

    @Inject TranslateAPI api;

    @BindView(R.id.word_to_translate) EditText etText;
    @BindView(R.id.translation) TextView tvTranslation;
    @BindView(R.id.fav_button) ImageButton favButton;
    private Disposable disposable;
    private boolean flag;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translator);
        ButterKnife.bind(this);
        intiDI();
        subscribe();
        flag = true;

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

            animator.setDuration(1000);
            animator.start();
        });
    }

    private void intiDI() {
        App.getApplication().getMainComponent().inject(this);
    }

    private void subscribe(){
        disposable = RxTextView.textChanges(etText)
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(charSequence -> charSequence.toString())
                .subscribe(string -> {
                    api.translate(API_KEY, string, "en-ru")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(word -> {
                                tvTranslation.setText(word.translation.get(0));
                            },throwable -> {
                                Snackbar.make(tvTranslation, "Error" + throwable.getCause(), Snackbar.LENGTH_LONG).show();});
        });
    }


//    private class TranslateObserver extends DisposableObserver<Word>{
//
//    }
}
