package org.berendeev.roma.yandexmobilization2017;

import android.content.Context;
import android.util.Pair;

import org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseHistoryDataSource;
import org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper;
import org.berendeev.roma.yandexmobilization2017.di.HistoryAndFavouritesModule;
import org.berendeev.roma.yandexmobilization2017.di.MainModule;
import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class HistoryRepoTest{

    private HistoryAndFavouritesRepository repository;
    private DatabaseHistoryDataSource historyDataSource;

    @Before
    public void before(){
        Context context = RuntimeEnvironment.application.getApplicationContext();
        MainModule mainModule = new MainModule(context.getApplicationContext());
        HistoryAndFavouritesModule module = new HistoryAndFavouritesModule();

        DatabaseOpenHelper openHelper = module.provideDatabaseOpenHelper(mainModule.provideContext());
        historyDataSource = (DatabaseHistoryDataSource) module.provideHistoryDataSource(openHelper);
        repository = module.provideHistoryAndFavouritesRepository(historyDataSource);
        System.out.println("");
    }

    @Test
    public void saveInHistoryTest(){
        Word word = buildWord("hello", "привет", true);
        repository.saveInHistory(word).subscribe();
        repository.getHistory().subscribe(words -> {
            assert word.equals(words.get(0));
            System.out.println(words);
        }, throwable -> {

        });
    }

    @Test
    public void saveInFavourite(){
        Word hello = buildWord("hello", "привет", true);
        Word world = buildWord("world", "мир", true);
        repository.saveInHistory(hello).subscribe();
        repository.saveInHistory(hello).subscribe();
        repository.saveInFavourites(world).subscribe();
        printFavourites();
        printHistory();
        printAll();
    }

    @Test
    public void removingTest(){
        Word hello = buildWord("hello", "привет", true);
        Word world = buildWord("world", "мир", false);
        Word world2 = buildWord("world", "мир", false);
//        repository.saveInHistory(hello).subscribe();
//        repository.saveInHistory(hello).subscribe();
        repository.saveInHistory(world).subscribe();
        repository.saveInFavourites(world).subscribe();
        printAll();
        repository.removeFromHistory(world).subscribe();
        printAll();
        repository.removeFromFavourites(world).subscribe();
        printAll();
        historyDataSource.clean();
        printAll();
    }

    private Word buildWord(String word, String translation, boolean isFavourite){
        return Word.create(word, translation, "en", "ru", isFavourite);
    }

    private void printHistory(){
        repository.getHistory().subscribe(words -> {
            System.out.println("history");
            System.out.println(words);
        });
    }

    private void printFavourites(){
        repository.getHistory().subscribe(words -> {
            System.out.println("favourite");
            System.out.println(words);
        });
    }

    private void printAll(){
        System.out.println("all:");
        List<Pair<Word, Boolean>> words = historyDataSource.getAll();
        for (Pair<Word, Boolean> word : words) {
            System.out.println(word.first.toString() + " in history " + word.second + " | " );
        }
    }
}
