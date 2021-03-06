package org.berendeev.roma.yandexmobilization2017.presentation.presenter;

import org.berendeev.roma.yandexmobilization2017.BuildConfig;
import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetFavouritesInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetHistoryInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.Interactor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.OnFavouritesChangedInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.OnHistoryChangedInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.RemoveAllFromFavouritesInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.RemoveAllFromHistoryInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.RemoveFromFavouritesInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.RemoveFromHistoryInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.SaveInFavouriteInteractor;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.SetWordInTranslatorInteractor;
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
    @Inject SaveInFavouriteInteractor saveInFavouriteInteractor;
    @Inject RemoveFromFavouritesInteractor removeFromFavouritesInteractor;
    @Inject RemoveFromHistoryInteractor removeFromHistoryInteractor;
    @Inject OnHistoryChangedInteractor onHistoryChangedInteractor;
    @Inject OnFavouritesChangedInteractor onFavouritesChangedInteractor;
    @Inject SetWordInTranslatorInteractor setWordInTranslatorInteractor;

    private int type;
    private Interactor<List<Word>, Void> getInteractor;
    private Interactor<Void, Void> deleteAllInteractor;
    private Interactor<Void, Word> deleteInteractor;
//    private Interactor<Integer, Void> changesInteractor;
    private WordListView view;
    private WordListView.Router router;
    private CompositeDisposable disposable;
    private int titleId;
    private List<Word> words;

    @Inject
    public HistoryPresenter() {
        disposable = new CompositeDisposable();
    }

    public void start() {
        loadWordList();
        setTitle();
//        subscribeOnChanges();
    }

//    private void subscribeOnChanges() {
//        disposable.add(changesInteractor.execute(new OnChangeObserver(), null));
//    }

    public void stop() {
        disposable.clear();
        router = null;
    }

    public void setView(WordListView view) {
        this.view = view;
    }

    public void setType(int type) {
        this.type = type;
        if (type == R.id.favourites_type) {
            getInteractor = getFavouritesInteractor;
            deleteInteractor = removeFromFavouritesInteractor;
            deleteAllInteractor = removeAllFromFavouritesInteractor;
            titleId = R.string.title_favourite;
//            changesInteractor = onFavouritesChangedInteractor;
        }
        if (type == R.id.history_type) {
            getInteractor = getHistoryInteractor;
            deleteInteractor = removeFromHistoryInteractor;
            deleteAllInteractor = removeAllFromHistoryInteractor;
//            changesInteractor = onHistoryChangedInteractor;
            titleId = R.string.title_history;
        }
    }

    public void onDeleteConfirm(int number) {
        Word word;
        try {
            word = words.get(number);
            deleteInteractor.execute(new OnChangeObserver(), word);
        }catch (IndexOutOfBoundsException e){
            if(BuildConfig.DEBUG){
                throw new IllegalArgumentException("wrong number in list");
            }
        }
    }

    public void onDeleteAllClick() {
        view.showDeleteAllDialog(type);
    }

    public void onDeleteAllConfirm(){
        deleteAllInteractor.execute(new OnChangeObserver(), null);

    }

    public void onFavButtonClick(int index) {
//        disposable.clear();
        Word word = words.get(index);
        if (word.isFavourite()) {
            word = word.toBuilder()
                    .isFavourite(false)
                    .build();
            view.switchOffFavButtonAt(index);
            removeFromFavouritesInteractor.execute(null, word);
        } else {
            word = word.toBuilder()
                    .isFavourite(true)
                    .build();
            view.switchOnFavButtonAt(index);
            saveInFavouriteInteractor.execute(null, word);
        }
        words.set(index, word);
    }

    public void onItemClick(int adapterPosition) {
        setWordInTranslatorInteractor.execute(null, words.get(adapterPosition));

        router.moveShowWordInTranslator();
    }

    public void setRouter(WordListView.Router router) {
        this.router = router;
    }

//    public void onShow() {
//        loadWordList();
//        subscribeOnChanges();
//    }
//
//    public void onHide() {
//        disposable.clear();
//    }

    private void loadWordList() {
        getInteractor.execute(new WordsObserver(), null);
    }

    private void setTitle() {
        view.setTitleById(titleId);
    }

    public boolean onItemLongClick(int adapterPosition) {
        view.showDeleteDialog(adapterPosition);
        return true;
    }

    private class WordsObserver extends DisposableObserver<List<Word>> {
        @Override public void onNext(List<Word> words) {
            view.showList(words);
            HistoryPresenter.this.words = words;
        }

        @Override public void onError(Throwable e) {
        }

        @Override public void onComplete() {
        }
    }

    private class OnChangeObserver extends DisposableObserver<Void> {

        @Override public void onNext(Void param) {
        }

        @Override public void onError(Throwable e) {
        }

        @Override public void onComplete() {
            loadWordList();
        }
    }

}
