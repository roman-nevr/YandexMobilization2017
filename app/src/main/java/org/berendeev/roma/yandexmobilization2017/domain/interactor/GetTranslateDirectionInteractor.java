package org.berendeev.roma.yandexmobilization2017.domain.interactor;



import android.util.Pair;

import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;
import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslateDirection;

import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetTranslateDirectionInteractor extends Interactor<Pair<TranslateDirection, TranslateDirection>, Void> {

    @Inject ResultRepository resultRepository;
    @Inject TranslationRepository translationRepository;

    @Inject
    public GetTranslateDirectionInteractor() {}

    @Override public Observable<Pair<TranslateDirection, TranslateDirection>> buildObservable(Void param) {
        return Observable.combineLatest(
                translationRepository.getLanguages(Locale.getDefault()),
                resultRepository.getTranslateDirection(),
                (map, stringStringPair) -> {
                    TranslateDirection directionFrom = buildTranslateDirection(stringStringPair.first, map.map());
                    TranslateDirection directionTo = buildTranslateDirection(stringStringPair.second, map.map());
                    Pair<TranslateDirection, TranslateDirection> pair = new Pair<>(directionFrom, directionTo);
                    return pair;
                }
        );
    }

    private TranslateDirection buildTranslateDirection(String key, Map<String, String> map){
        return TranslateDirection.builder()
                .key(key)
                .name(map.get(key))
                .build();
    }
}
