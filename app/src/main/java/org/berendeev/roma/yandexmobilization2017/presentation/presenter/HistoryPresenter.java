package org.berendeev.roma.yandexmobilization2017.presentation.presenter;

import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetFavouritesInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetHistoryInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.Interactor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.RemoveAllFromFavouritesInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.RemoveAllFromHistoryInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.VoidObserver;
import org.berendeev.roma.yandexmobilization2017.presentation.view.WordListView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class HistoryPresenter {

    @Inject GetHistoryInteractor getHistoryInteractor;
    @Inject GetFavouritesInteractor getFavouritesInteractor;
    @Inject RemoveAllFromFavouritesInteractor removeAllFromFavouritesInteractor;
    @Inject RemoveAllFromHistoryInteractor removeAllFromHistoryInteractor;

    private int type;
    private Interactor<List<Word>, Void> getInteractor;
    private Interactor<Void, Void> deleteAllInteractor;
    private WordListView view;
    private CompositeDisposable disposable;
    private int titleId;

    @Inject
    public HistoryPresenter() {
        disposable = new CompositeDisposable();
    }

    public void start(){
        disposable.add(getInteractor.execute(new WordsObserver(), null));
    }

    public void stop(){
        disposable.clear();
    }

    public void setView(WordListView view) {
        this.view = view;
    }

    public void setType(int type) {
        this.type = type;
        if(type == R.id.favourites_type){
            getInteractor = getFavouritesInteractor;
            deleteAllInteractor = removeAllFromFavouritesInteractor;
            titleId = R.string.title_favourite;
        }
        if (type == R.id.history_type){
            getInteractor = getHistoryInteractor;
            deleteAllInteractor = removeAllFromHistoryInteractor;
            titleId = R.string.title_history;
        }
    }

    public void deleteAll() {
        //TODO delete interactor
        deleteAllInteractor.execute(new VoidObserver(), null);
    }

    private class WordsObserver extends DisposableObserver<List<Word>>{
        @Override public void onNext(List<Word> words) {
            view.showList(words);
        }

        @Override public void onError(Throwable e) {
        }

        @Override public void onComplete() {
        }
    }


}
