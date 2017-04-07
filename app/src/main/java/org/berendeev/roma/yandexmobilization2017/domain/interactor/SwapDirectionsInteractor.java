package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SwapDirectionsInteractor extends Interactor<Void, Void> {

    @Inject PreferencesRepository preferencesRepository;
    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;

    @Inject
    public SwapDirectionsInteractor() {
    }

    @Override public Observable<Void> buildObservable(Void param) {
//        return preferencesRepository.swapDirections().toObservable();

//        return preferencesRepository
//                .getLastWord()
//                .map(word -> swapMap(word))
//                .flatMap(word -> historyAndFavouritesRepository
//                        .saveInHistory(word)
//                        .toObservable())
//                .flatMap(o -> {
//                    sw
//                }
        Observable<Word> cache = preferencesRepository
                .swapDirections()
                .andThen(preferencesRepository
                        .getLastWord())
                .map(word -> swapMap(word))
                .cache();

        return Observable.merge(
                cache.flatMap(word ->
                    historyAndFavouritesRepository
                            .saveInHistory(word).toObservable()),
                cache.flatMap(word ->
                preferencesRepository.saveLastWord(word).toObservable()));

//        return preferencesRepository
//                .swapDirections()
//                .andThen(preferencesRepository
//                        .getLastWord())
//                .map(word -> swapMap(word))
//                .flatMap(word -> historyAndFavouritesRepository
//                        .saveInHistory(word).toObservable());
    }

    private Word swapMap(Word word) {
        return word.toBuilder()
                .translation(word.word())
                .word(word.translation())
//                .languageTo(word.languageFrom())
//                .languageFrom(word.languageTo())
                .isFavourite(false)
                .build();
    }
}
