package org.berendeev.roma.yandexmobilization2017.domain.interactor;



import android.util.Pair;

import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;
import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslateDirection;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;

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
                resultRepository.getQueryObservable(),
                (map, query) -> {
                    TranslateDirection directionFrom = buildTranslateDirection(query.langFrom(), map.map());
                    TranslateDirection directionTo = buildTranslateDirection(query.langTo(), map.map());
                    return new Pair<>(directionFrom, directionTo);
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
