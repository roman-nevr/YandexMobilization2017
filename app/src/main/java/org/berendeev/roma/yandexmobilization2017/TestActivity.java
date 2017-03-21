package org.berendeev.roma.yandexmobilization2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import org.berendeev.roma.yandexmobilization2017.data.http.TranslateAPI;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.presentation.App;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static org.berendeev.roma.yandexmobilization2017.Consts.API_KEY;


public class TestActivity extends AppCompatActivity {

    private TranslateAPI api;

    @BindView(R.id.word_to_translate) EditText etText;
    @BindView(R.id.translation) TextView tvTranslation;
    private Disposable disposable;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translator);
        ButterKnife.bind(this);
        intiDI();
        subscribe();
    }

    private void intiDI() {
        api = App.getApplication().getMainComponent().provideTranslateAPI();
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
                            },throwable -> {});
        });
    }

//    private class TranslateObserver extends DisposableObserver<Word>{
//
//    }
}
