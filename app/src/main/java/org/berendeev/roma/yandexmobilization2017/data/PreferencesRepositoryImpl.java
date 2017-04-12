package org.berendeev.roma.yandexmobilization2017.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import com.google.gson.Gson;

import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;


public class PreferencesRepositoryImpl implements PreferencesRepository {

    private static final String WORD = "word";
    private static final String TEXT = "text";
    private static final String DIRECTIONS = "dirs";
    private static final String TRANSLATION = "translation";
    private static final String DIRECTION_TO = "to";
    private static final String DIRECTION_FROM = "from";
    private static final String DICTIONARY = "dictionary";
    private static final String TRANSLATION_STATE = "state";

    Context context;
    private final SharedPreferences wordPreferences, dirsPreferences;
    private BehaviorSubject<Pair<String, String>> directionsSubject;//FROM - TO
    private BehaviorSubject<Word> lastWordSubject;

    private Gson gson;

    @Inject
    public PreferencesRepositoryImpl(Context context, Gson gson) {
        this.context = context;
        this.wordPreferences = context.getSharedPreferences(WORD, Context.MODE_PRIVATE);
        this.dirsPreferences = context.getSharedPreferences(DIRECTIONS, Context.MODE_PRIVATE);
        this.gson = gson;
        initDirections();
        initLastWord();
    }

    @Override public Completable saveLastWord(Word word) {
        return Completable.fromAction(() -> {
            lastWordSubject.onNext(word);
            wordPreferences.edit()
                    .putString(TEXT, word.word())
                    .putString(TRANSLATION, word.translation())
                    .putString(TRANSLATION_STATE, word.translationState().name())
                    .putString(DICTIONARY, gson.toJson(word.dictionary()))
                    .apply();
        });
    }

    @Override public Observable<Word> getLastWord() {
        return lastWordSubject;

    }

    @Override public Observable<Pair<String, String>> getTranslateDirection() {
        return directionsSubject;
    }

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
        String defaultDirectionFrom;
        if(Locale.getDefault().getLanguage().equals("en")){
            defaultDirectionFrom = "ru";
        }else {
            defaultDirectionFrom = "en";
        }
        String directionFrom = dirsPreferences.getString(DIRECTION_FROM, defaultDirectionFrom);
        directionsSubject.onNext(new Pair<>(directionFrom, directionTo));
        saveDirection(DIRECTION_FROM, directionFrom);
        saveDirection(DIRECTION_TO, directionTo);
    }

    private void initLastWord() {
        Word word = Word.builder()
                .word(wordPreferences.getString(TEXT, ""))
                .translation(wordPreferences.getString(TRANSLATION, ""))
                .languageFrom(dirsPreferences.getString(DIRECTION_FROM, directionsSubject.getValue().first))
                .languageTo(dirsPreferences.getString(DIRECTION_TO, directionsSubject.getValue().second))
                .dictionary(gson.fromJson(wordPreferences.getString(DICTIONARY, "{\"text\":\"\",\"transcription\":\"\",\"definitions\":[]}"), Dictionary.class))
                .translationState(Word.TranslationState.valueOf(wordPreferences.getString(TRANSLATION_STATE, Word.TranslationState.ok.name())))
                .isFavourite(false)
                .build();
        lastWordSubject = BehaviorSubject.createDefault(word);

    }
}
