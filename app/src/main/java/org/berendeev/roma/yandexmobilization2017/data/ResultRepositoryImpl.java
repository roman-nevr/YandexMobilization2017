package org.berendeev.roma.yandexmobilization2017.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

import static org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState.ok;
import static org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState.requested;


public class ResultRepositoryImpl implements ResultRepository {

    private static final String WORD = "word";
    private static final String TEXT = "text";
    private static final String DIRECTIONS = "dirs";
    private static final String TRANSLATION = "translation";
    private static final String DIRECTION_TO = "to";
    private static final String DIRECTION_FROM = "from";
    private static final String DICTIONARY = "dictionary";
    private static final String TRANSLATION_STATE = "state";
    public static final String TIME = "time";

    Context context;
    private final SharedPreferences resultPreferences, queryPreferences;
    private BehaviorSubject<Word> resultSubject;
    private BehaviorSubject<TranslationQuery> lastQuerySubject;
    private Gson gson;

    //используем SharedPreferences т.к. храним пары ключ-значение

    @Inject
    public ResultRepositoryImpl(Context context, Gson gson) {
        this.context = context;
        this.resultPreferences = context.getSharedPreferences(WORD, Context.MODE_PRIVATE);
        this.queryPreferences = context.getSharedPreferences(DIRECTIONS, Context.MODE_PRIVATE);
        this.gson = gson;
        initLastQuery();
        initLastWord();
    }

    @Override public Completable saveResult(Word word) {
        return Completable.fromAction(() -> {
            //если результат пришел на последний запрос, то сохраняем
            //если это опоздавший результат, то не сохраняем
            if(lastQuerySubject.getValue().equals(TranslationQuery.create(word.word(), word.languageFrom(), word.languageTo()))){
                resultSubject.onNext(word);
                saveWord(word);
            }
        });
    }

    @Override public Observable<Word> getResultObservable() {
        return resultSubject;

    }

    @Override public Completable saveLastQuery(TranslationQuery query) {
        return Completable.fromAction(() -> {
            saveQuery(query);
            lastQuerySubject.onNext(query);
        });
    }

    @Override public Completable invalidateResult() {
        return Completable.fromAction(() -> {
            Word word = resultSubject.getValue().toBuilder()
                    .translationState(requested)
                    .queryTime(System.currentTimeMillis())
                    .build();
            saveWord(word);
            resultSubject.onNext(word);
        });
    }

    private void saveQuery(TranslationQuery query) {
        queryPreferences.edit()
                .putString(TEXT, query.text())
                .putString(DIRECTION_FROM, query.langFrom())
                .putString(DIRECTION_TO, query.langTo())
                .apply();
    }

    @Override public Observable<TranslationQuery> getQueryObservable() {
        return lastQuerySubject;
    }

    @Override public Completable setDirectionTo(String to) {
        return Completable.fromAction(() -> {
            String from;
            if(to.equals(getFrom())){
                from = getTo();
            }else {
                from = getFrom();
            }
            saveDirections(from, to);
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
            saveDirections(from, to);
        });
    }

    private String getTo() {
        return lastQuerySubject.getValue().langTo();
    }

    private String getFrom() {
        return lastQuerySubject.getValue().langFrom();
    }

    private void saveDirections(String from, String to){
        TranslationQuery query = lastQuerySubject.getValue().toBuilder()
                .langFrom(from)
                .langTo(to)
                .build();
        saveQuery(query);
        lastQuerySubject.onNext(query);
    }

    private void initLastWord() {
        Word.TranslationState state = Word.TranslationState.valueOf(resultPreferences.getString(TRANSLATION_STATE, ok.name()));
        if (state != ok){
            state = requested;
        }
        Word word = Word.builder()
                .word(resultPreferences.getString(TEXT, ""))
                .translation(resultPreferences.getString(TRANSLATION, ""))
                .languageFrom(queryPreferences.getString(DIRECTION_FROM, lastQuerySubject.getValue().langFrom()))
                .languageTo(queryPreferences.getString(DIRECTION_TO, lastQuerySubject.getValue().langTo()))
                .dictionary(gson.fromJson(resultPreferences.getString(DICTIONARY, "{\"text\":\"\",\"transcription\":\"\",\"definitions\":[]}"), Dictionary.class))
                .translationState(state)
                .isFavourite(false)
                .queryTime(resultPreferences.getLong(TIME, System.currentTimeMillis()))
                .build();
        resultSubject = BehaviorSubject.createDefault(word);

    }

    private void initLastQuery() {
        String query = queryPreferences.getString(TEXT, "");
        String directionTo = queryPreferences.getString(DIRECTION_TO, Locale.getDefault().getLanguage());
        String defaultDirectionFrom;
        if(Locale.getDefault().getLanguage().equals("en")){
            defaultDirectionFrom = "ru";
        }else {
            defaultDirectionFrom = "en";
        }
        String directionFrom = queryPreferences.getString(DIRECTION_FROM, defaultDirectionFrom);
        lastQuerySubject = BehaviorSubject.createDefault(TranslationQuery.create(query, directionFrom, directionTo));
//        saveDirection(DIRECTION_FROM, directionFrom);
//        saveDirection(DIRECTION_TO, directionTo);
    }

    private void saveWord(Word word) {
        resultPreferences.edit()
                .putString(TEXT, word.word())
                .putString(TRANSLATION, word.translation())
                .putString(TRANSLATION_STATE, word.translationState().name())
                .putString(DICTIONARY, gson.toJson(word.dictionary()))
                .putLong(TIME, word.queryTime())
                .apply();
    }
}
