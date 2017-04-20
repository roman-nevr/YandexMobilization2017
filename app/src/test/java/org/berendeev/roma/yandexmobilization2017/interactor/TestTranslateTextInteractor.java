package org.berendeev.roma.yandexmobilization2017.interactor;

import org.berendeev.roma.yandexmobilization2017.domain.ResultRepository;
import org.berendeev.roma.yandexmobilization2017.domain.interactor.TranslateTextInteractor;

import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;


public class TestTranslateTextInteractor extends TranslateTextInteractor {
    public ResultRepository resultRepository;

    public ThreadPoolExecutor workExecutor;
    public Scheduler mainExecutor;

    public TestTranslateTextInteractor(ResultRepository resultRepository, ThreadPoolExecutor workExecutor, Scheduler mainExecutor) {
        this.resultRepository = resultRepository;
        this.workExecutor = workExecutor;
        this.mainExecutor = mainExecutor;
    }

    @Override public Observable<Void> buildObservable(String param) {
        return super.buildObservable(param);
    }
}
