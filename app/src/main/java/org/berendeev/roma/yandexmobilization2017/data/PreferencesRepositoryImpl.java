package org.berendeev.roma.yandexmobilization2017.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.internal.operators.completable.CompletableFromAction;
import io.reactivex.subjects.BehaviorSubject;


public class PreferencesRepositoryImpl implements PreferencesRepository {

    public static final String WORD = "word";
    public static final String TEXT = "text";
    public static final String DIRECTIONS = "dirs";
    public static final String TRANSLATION = "translation";
    public static final String DIRECTION_TO = "to";
    public static final String DIRECTION_FROM = "from";

    Context context;
    private final SharedPreferences wordPreferences, dirsPreferences;
    private BehaviorSubject<Pair<String, String>> directionsSubject;//FROM - TO

    @Inject
    public PreferencesRepositoryImpl(Context context) {
        this.context = context;
        this.wordPreferences = context.getSharedPreferences(WORD, Context.MODE_PRIVATE);
        this.dirsPreferences = context.getSharedPreferences(DIRECTIONS, Context.MODE_PRIVATE);
        initDirections();
    }

    @Override public Completable saveLastWord(Word word) {
        return Completable.fromAction(() -> {
            wordPreferences.edit()
                    .putString(TEXT, word.word())
                    .putString(TRANSLATION, word.translation())
                    .apply();
//            dirsPreferences.edit()
//                    .putString(DIRECTION_FROM, word.languageFrom())
//                    .putString(DIRECTION_TO, word.languageTo())
//                    .apply();
        });
    }

    @Override public Single<Word> getLastWord() {
        return Single.just(Word.builder()
                .word(wordPreferences.getString(TEXT, ""))
                .translation(wordPreferences.getString(TRANSLATION, ""))
                .languageFrom(dirsPreferences.getString(DIRECTION_FROM, ""))
                .languageTo(dirsPreferences.getString(DIRECTION_TO, ""))
                .isFavourite(false)
                .build());
    }

    @Override public Observable<Pair<String, String>> getTranslateDirection() {
        return directionsSubject;
    }


    //TODO wrap to code reuse
    @Override public Completable setDirectionTo(String to) {
        return Completable.fromAction(() -> {
            String from;
            if(to.equals(getFrom())){
                from = getTo();
            }else {
                from = getFrom();
            }
            changeDirections(from, to);
        });
    }

    @Override public Completable setDirectionFrom(String from) {
        return Completable.fromAction(() -> {
            String to;
            if(from.equals(getTo())){
                to = getFrom();
            }else {
                to = getTo();
            }
            changeDirections(from, to);
        });
    }

    @Override public Completable swapDirections() {
        return Completable.fromAction(() -> {
            String from = getFrom();
            String to = getTo();
            changeDirections(to, from);
        });
    }

    private void changeDirections(String from, String to){
        saveDirection(DIRECTION_FROM, from);
        saveDirection(DIRECTION_TO, to);
        directionsSubject.onNext(new Pair<>(from, to));
    }

    private void saveDirection(String direction, String value){
        dirsPreferences.edit()
                .putString(direction, value)
                .apply();
    }

    private String getFrom(){
        return directionsSubject.getValue().first;
    }

    private String getTo(){
        return directionsSubject.getValue().second;
    }

    private void initDirections(){
        directionsSubject = BehaviorSubject.create();
        String directionTo = dirsPreferences.getString(DIRECTION_TO, Locale.getDefault().getLanguage());
        String defaultDirectionFrom = null;
        if(Locale.getDefault().getLanguage().equals("en")){
            defaultDirectionFrom = "ru";
        }else {
            defaultDirectionFrom = "en";
        }
        String directionFrom = dirsPreferences.getString(DIRECTION_FROM, defaultDirectionFrom);
        directionsSubject.onNext(new Pair<>(directionFrom, directionTo));
    }

    private void swapLanguages(){
        directionsSubject.onNext(new Pair<>(getTo(), getTo()));
    }
}
