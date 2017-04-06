package org.berendeev.roma.yandexmobilization2017;

import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
import org.berendeev.roma.yandexmobilization2017.domain.exception.ConnectionException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Example locale unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("ru", "Russian");
        map.put("en", "English");

        LanguageMap languageMap = LanguageMap.builder()
                .map(map)
                .locale(Locale.getDefault())
                .build();

        Set<Map.Entry<String, String>> entries = languageMap.map().entrySet();

        ArrayList<Map.Entry<String, String>> list = new ArrayList<>(entries);

        System.out.println(list);
    }

    @Test
    public void rxTest(){
        try {
            Observable.fromCallable(() -> {
                throw new ConnectionException();
            }).subscribeWith(new Obs());
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
    }

    private class Obs extends DisposableObserver<Object>{
        @Override public void onNext(Object o) {

        }

        @Override public void onError(Throwable e) {
            System.out.println(e.toString());
        }

        @Override public void onComplete() {

        }
    }
}