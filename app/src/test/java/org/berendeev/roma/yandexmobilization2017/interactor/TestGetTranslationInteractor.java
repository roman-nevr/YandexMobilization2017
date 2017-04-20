package org.berendeev.roma.yandexmobilization2017.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.DictionaryRepository;
import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;
import org.berendeev.roma.yandexmobilization2017.domain.TranslationRepository;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.GetTranslationInteractor;

import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Inject;

import io.reactivex.Scheduler;


public class TestGetTranslationInteractor extends GetTranslationInteractor {
    public TranslationRepository translationRepository;
    public HistoryAndFavouritesRepository historyAndFavouritesRepository;
    public ResultRepository resultRepository;
    public DictionaryRepository dictionaryRepository;

    public ThreadPoolExecutor workExecutor;
    public Scheduler mainExecutor;

    public TestGetTranslationInteractor(TranslationRepository translationRepository, HistoryAndFavouritesRepository historyAndFavouritesRepository, ResultRepository resultRepository, DictionaryRepository dictionaryRepository, ThreadPoolExecutor workExecutor, Scheduler mainExecutor) {
        this.translationRepository = translationRepository;
        this.historyAndFavouritesRepository = historyAndFavouritesRepository;
        this.resultRepository = resultRepository;
        this.dictionaryRepository = dictionaryRepository;
        this.workExecutor = workExecutor;
        this.mainExecutor = mainExecutor;
    }
}
