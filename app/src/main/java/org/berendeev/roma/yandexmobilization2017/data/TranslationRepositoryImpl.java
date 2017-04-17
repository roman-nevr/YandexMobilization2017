package org.berendeev.roma.yandexmobilization2017.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.berendeev.roma.yandexmobilization2017.BuildConfig;
import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.data.entity.Languages;
import org.berendeev.roma.yandexmobilization2017.data.entity.Translation;
import org.berendeev.roma.yandexmobilization2017.data.http.OfflineLanguages;
import org.berendeev.roma.yandexmobilization2017.data.http.TranslateApi;
import org.berendeev.roma.yandexmobilization2017.data.mapper.LanguageMapper;
import org.berendeev.roma.yandexmobilization2017.data.mapper.TranslateMapper;
import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.domain.exception.ConnectionException;
import org.berendeev.roma.yandexmobilization2017.domain.exception.TranslationException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Single;
import timber.log.Timber;

import static org.berendeev.roma.yandexmobilization2017.data.http.TranslateApi.OK_CODE;

public class TranslationRepositoryImpl implements TranslationRepository {


    private final Context context;
    private final Gson gson;
    private TranslateApi translateApi;

    public TranslationRepositoryImpl(TranslateApi translateApi, Context context, Gson gson) {
        this.context = context;
        this.translateApi = translateApi;
        this.gson = gson;
    }

    @Override public Single<Translation> translate(TranslationQuery query) {
        if (query.text().equals("")) {
            return Single.just(new Translation(0, query.langFrom() + "-" + query.langTo(), new ArrayList<>()));
        }

        if (!isNetworkAvailable()) {
            throw new ConnectionException();
        }
        return Single.fromCallable(() -> {
            try {
//                Thread.sleep(10000);
                Translation translation = translateApi
                        .translate(BuildConfig.TRANSLATE_API_KEY, query.text(), query.langFrom() + "-" + query.langTo())
                        .blockingGet();
                if (translation.code == OK_CODE) {
                    return translation;
                } else {
                    throw new TranslationException();
                }
            } catch (Throwable throwable) {
                if (throwable instanceof TranslationException) {
                    throw throwable;
                }
                throw new ConnectionException(throwable);
            }
        });
    }

    @Override public Observable<LanguageMap> getLanguages(Locale locale) {
        return Observable.fromCallable(() -> {
            if (!isNetworkAvailable()) {
                return LanguageMapper.map(getOfflineLanguages(locale), locale);
            }
            try {
                Languages languages = translateApi
                        .getLanguages(BuildConfig.TRANSLATE_API_KEY, locale.getLanguage())
                        .onErrorReturn(throwable -> getOfflineLanguages(locale))
                        .blockingGet();
                return LanguageMapper.map(languages, locale);
            } catch (Throwable throwable) {
                throw new ConnectionException(throwable);
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private Languages getOfflineLanguages(Locale locale){
        return OfflineLanguages.getLanguage(context, locale.getLanguage(), gson);
    }

}
