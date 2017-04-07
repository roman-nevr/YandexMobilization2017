package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

public class TranslateTextInteractor extends Interactor<Word, TranslationQuery> {

    @Inject TranslationRepository translationRepository;
    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;
    @Inject PreferencesRepository preferencesRepository;

    @Inject
    public TranslateTextInteractor() {}

    @Override public Observable<Word> buildObservable(TranslationQuery param) {
        return Observable.concat(
                historyAndFavouritesRepository
                        .getWord(param)
                        .toObservable(),
                translationRepository
                        .translate(param)
        )
                .firstElement()
                .flatMapCompletable(word ->
                        preferencesRepository.saveLastWord(word)
                ).toObservable();
    }
}
