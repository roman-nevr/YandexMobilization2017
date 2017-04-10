package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;

public class SwapDirectionsInteractor extends Interactor<Void, Void> {

    @Inject PreferencesRepository preferencesRepository;
    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;

    @Inject
    public SwapDirectionsInteractor() {
    }

    @Override public Observable<Void> buildObservable(Void param) {

        Word word = preferencesRepository.getLastWord().map(word1 -> swapMap(word1)).blockingFirst();

        return Observable.merge(
                historyAndFavouritesRepository
                        .saveInHistory(word).toObservable(),
                preferencesRepository.saveLastWord(word).toObservable(),
                preferencesRepository.swapDirections().toObservable());
    }

    private Word swapMap(Word word) {
        return word.toBuilder()
                .translation(word.word())
                .word(word.translation())
                .languageFrom(word.languageTo())
                .languageTo(word.languageFrom())
                .isFavourite(false)
                .build();
    }
}
