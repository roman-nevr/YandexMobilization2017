package org.berendeev.roma.yandexmobilization2017;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
import org.berendeev.roma.yandexmobilization2017.domain.exception.ConnectionException;
import org.berendeev.roma.yandexmobilization2017.rx.LongSingle;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
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

    @Test
    public void compositeTest(){
        CompositeDisposable disposable = new CompositeDisposable();
        for(int i = 0; i < 1000; i++){
            Observable<Integer> observable = Observable.just(1, 2);
            disposable.add(observable.subscribe(integer -> {
                System.out.println(integer);
            }));
        }
        int a = 0;
    }

    @Test
    public void span(){
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("text");
        StyleSpan boldSpan = new StyleSpan( Typeface.BOLD );
        builder.setSpan(boldSpan, 0, builder.length(), Spannable.SPAN_COMPOSING);
//        Spannable spannable = new S
    }

    @Test
    public void complete(){
        Observable<Integer> observable = Observable.create(e -> {
            e.onNext(1);
        });
        observable
                .map(integer -> "" + integer)
                .flatMap(s -> {
                    if (s.equals("1")){
                        return Observable.just(2);
                    }else {
                        return Observable.just(3);
                    }
                })
                .subscribe(integer -> {
                    System.out.println(integer);
                }, throwable -> {}, () -> {
                    System.out.println("complete");
                });
    }

    @Test
    public void delay() throws InterruptedException {
        Observable.just(1)
                .delay(10000, TimeUnit.MILLISECONDS)
                .subscribe(integer -> System.out.println(integer),
                        throwable -> {},
                        () -> {
                    System.out.println("complete");
                });
        Thread.sleep(100000);
    }

    @Test
    public void singleError() throws Exception {
        Single<Integer> objectSingle = LongSingle.fromCallable(() -> {
            try {
                Thread.sleep(10000);
                return 1;
            }catch (Throwable throwable){
                return 0;
//                throw new Exception(throwable);
            }
            //throw new IllegalArgumentException();
        });
//        Observable<Integer> objectSingle = Observable.create(emitter -> {
//            try {
//                Thread.sleep(10000);
//            }catch (Throwable throwable){
//                emitter.onError(new Exception(throwable));
//            }
//            emitter.onNext(1);
//        });

//        Single<Integer> objectSingle = Single.create(emitter -> {
//            try {
//                Thread.sleep(10000);
//                if (!emitter.isDisposed()){
//                    emitter.onSuccess(1);
//                }
//            }catch (Throwable throwable){
//                if (!emitter.isDisposed()){
//                    emitter.onError(new Exception(throwable));
//                }
//            }
//        });

        Disposable subscribe = objectSingle
//                .onErrorResumeNext(Single.just(2))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
//                .subscribe(o -> {
//                            System.out.println(o);
//                        },
//                        throwable -> {
//                            throwable.printStackTrace();
//                        },
//                        () -> {
//                            System.out.println("complete");
//                        });
        .subscribe((integer, throwable) -> {
            int a = 0;
            if (throwable == null){
                System.out.println(integer);
            }else {
                throwable.printStackTrace();
            }
        });
        Thread.sleep(1000);
        subscribe.dispose();
        Thread.sleep(100000);
    }

}