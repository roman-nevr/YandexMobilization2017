package org.berendeev.roma.yandexmobilization2017.data;

import org.berendeev.roma.yandexmobilization2017.data.sqlite.HistoryDataSource;
import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.TranslationQuery;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

import static org.berendeev.roma.yandexmobilization2017.domain.entity.Word.TranslationState.ok;


public class HistoryAndFavouritesRepositoryImpl implements HistoryAndFavouritesRepository {

    //репозитарий для истории и избранного
    //Один класс, т.к. хранение истории и избранного почти не отличиается
    //здесь мы используем источник данных, который инкапсулирует механизм хранения
    private HistoryDataSource historyDataSource;

    public HistoryAndFavouritesRepositoryImpl(HistoryDataSource historyDataSource) {
        this.historyDataSource = historyDataSource;
    }

    @Override public Observable<List<Word>> getHistory() {
        return historyDataSource.getHistory();
    }

    @Override public Observable<List<Word>> getFavourites() {
        return historyDataSource.getFavourites();
    }

    @Override public Single<Word> checkIfInFavourites(Word word) {
        if (historyDataSource.checkIfInFavourites(word)) {
            return Single
                    .just(word
                            .toBuilder()
                            .isFavourite(true)
                            .build());
        } else {
            return Single.just(word
                    .toBuilder()
                    .isFavourite(false)
                    .build());
        }
    }

    @Override public Completable saveInHistory(Word word) {
        if (word.word().isEmpty() || word.translationState() != ok) {
            return Completable.complete();
        } else {
            return historyDataSource.saveInHistory(word);
        }
    }

    @Override public Completable saveInFavourites(Word word) {
        if (word.word().isEmpty() || word.translationState() != ok) {
            return Completable.complete();
        } else {
            return historyDataSource.saveInFavourites(word);
        }
    }

    @Override public Completable removeFromHistory(Word word) {
        return historyDataSource.removeFromHistory(word);
    }

    @Override public Completable removeAllFromHistory() {
        return historyDataSource.removeAllFromHistory();
    }

    @Override public Completable removeFromFavourites(Word word) {
        return historyDataSource.removeFromFavourites(word);
    }

    @Override public Completable removeAllFromFavourites() {
        return historyDataSource.removeAllFromFavourites();
    }

    @Override public Observable<Integer> getOnChangeObservable() {
        return historyDataSource.getOnChangeObservable();
    }

    @Override public Maybe<Word> getWord(TranslationQuery query) {
        return Maybe.create(emitter -> {
            try {
                Word word = historyDataSource.getWord(query);
                if (emitter.isDisposed()) {
                    return;
                }
                if (word == Word.EMPTY) {
                    emitter.onComplete();
                } else {
                    emitter.onSuccess(word);
                }
            } catch (Throwable throwable) {
                if (emitter.isDisposed()) {
                    return;
                }
                emitter.onError(throwable);
            }
        });
    }
}
