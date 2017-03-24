package org.berendeev.roma.yandexmobilization2017;

import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
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
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

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
        Thread thread = Thread.currentThread();
        TestObserver<Integer> testObserver = TestObserver.create();
        CompositeDisposable disposable = new CompositeDisposable();
        Observable<Integer> observable1 = Observable.create(e -> {
            int a = 0;
            while (a < 10){
                try {
                    Thread.sleep(100);
                }catch (Exception e1){
                }
                e.onNext(10 + a++);
            }
        });
        Disposable disposable1 = observable1
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.single())
                .subscribeWith(testObserver);
        Observable<Integer> observable2 = Observable.create(e -> {
            while (true){
                int a = 0;
                while (a < 10){
                    try {
                        Thread.sleep(100);
                    }catch (Exception e1){
                    }
                    e.onNext(a++);
                }
            }
        });
        Disposable disposable2 = observable2
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.single())
                .subscribeWith(testObserver);
        disposable.add(disposable1);
        disposable.add(disposable2);
        disposable1.dispose();
        observable1.subscribe();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(disposable2.isDisposed()){
            System.out.println("1111");
        }
    }
}