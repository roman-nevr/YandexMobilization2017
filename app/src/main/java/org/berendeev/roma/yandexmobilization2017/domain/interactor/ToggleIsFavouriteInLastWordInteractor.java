package org.berendeev.roma.yandexmobilization2017.domain.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.PreferencesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class ToggleIsFavouriteInLastWordInteractor extends Interactor<Void, Word> {
    @Inject HistoryAndFavouritesRepository historyAndFavouritesRepository;
    @Inject PreferencesRepository preferencesRepository;

    @Inject
    public ToggleIsFavouriteInLastWordInteractor() {
    }

    @Override public Observable<Void> buildObservable(Word param) {

        return preferencesRepository
                .getLastWord()
                .flatMap(lastWord ->
                    historyAndFavouritesRepository
                            .getWord(TranslationQuery.create(lastWord.word(), lastWord.languageFrom(), lastWord.languageTo()))
                            .toObservable()
                            .flatMap(word -> {
                                if (word.isFavourite()){
                                    return historyAndFavouritesRepository
                                            .removeFromFavourites(word)
                                            .toObservable();
                                }else {
                                    return historyAndFavouritesRepository
                                            .saveInFavourites(word)
                                            .toObservable();
                                }
                            })
                );

//        Word word = preferencesRepository
//                .getLastWord()
//                .blockingFirst();
//        Completable favoutitesAction;
//        if(word.isFavourite()){
//            favoutitesAction = historyAndFavouritesRepository
//                    .removeFromFavourites(word);
//        }else{
//            favoutitesAction = historyAndFavouritesRepository
//                    .saveInFavourites(word);
//        }
//        return Observable.merge(
//                favoutitesAction.toObservable(),
//                preferencesRepository
//                .saveLastWord(word.toBuilder()
//                                        .isFavourite(!word.isFavourite())
//                                        .build())
//                .toObservable()
//        );
    }
}
