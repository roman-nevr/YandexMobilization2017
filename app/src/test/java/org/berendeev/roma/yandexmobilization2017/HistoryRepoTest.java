package org.berendeev.roma.yandexmobilization2017;

import android.content.Context;
import android.util.Pair;

import org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseHistoryDataSource;
import org.berendeev.roma.yandexmobilization2017.data.sqlite.DatabaseOpenHelper;
import org.berendeev.roma.yandexmobilization2017.di.HistoryAndFavouritesModule;
import org.berendeev.roma.yandexmobilization2017.di.MainModule;
import org.berendeev.roma.yandexmobilization2017.domain.HistoryAndFavouritesRepository;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE)
public class HistoryRepoTest{

    private HistoryAndFavouritesRepository repository;
    private DatabaseHistoryDataSource historyDataSource;

    @Before
    public void before(){
        Context context = RuntimeEnvironment.application.getApplicationContext();
        MainModule mainModule = new MainModule(context.getApplicationContext());
        HistoryAndFavouritesModule module = new HistoryAndFavouritesModule();

        DatabaseOpenHelper openHelper = mainModule.provideDatabaseOpenHelper(mainModule.provideContext());
        historyDataSource = (DatabaseHistoryDataSource) mainModule.provideHistoryDataSource(openHelper);
        repository = mainModule.provideHistoryAndFavouritesRepository(historyDataSource);
        System.out.println("");
    }

    @Test
    public void saveInHistoryTest(){
        Word word = buildWord("hello", "привет", false);
        repository.saveInHistory(word).subscribe();
        repository.saveInHistory(word).subscribe();
        printAll();
        repository.saveInFavourites(word).subscribe();
        repository.getHistory().subscribe(words -> {
            assert word.equals(words.get(0));
            assert words.size() == 1;
            //System.out.println(words);
        }, throwable -> {

        });
        printAll();
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
        repository.saveInHistory(world).subscribe();
        repository.saveInFavourites(world).subscribe();
        printAll();
        repository.removeFromFavourites(world).subscribe();
        printAll();
        repository.removeFromHistory(world).subscribe();
        printAll();
    }

    @Test
    public void getHistoryAndGetFavouritesTest(){
        Word hello = buildWord("hello", "привет", true);
        Word world = buildWord("world", "мир", false);
        repository.saveInHistory(hello).subscribe();
        repository.saveInHistory(world).subscribe();
        repository.getHistory().subscribe(words -> {
            System.out.println("history:");
            System.out.println(words);
            Assert.assertTrue( words.size() == 2);
        });
        repository.saveInFavourites(world).subscribe();
        repository.getFavourites().subscribe(words -> {
            System.out.println("favourites:");
            System.out.println(words);
            Assert.assertTrue( words.size() == 2);
        });
        printAll();
    }

    @Test
    public void removeAllTest(){
        Word hello = buildWord("hello", "привет", true);
        Word hello1 = buildWord("hello1", "привет", true);
        Word hello2 = buildWord("hello2", "привет", true);
        Word hello3 = buildWord("hello3", "привет", true);
        repository.saveInHistory(hello).subscribe();
        repository.saveInHistory(hello1).subscribe();
        repository.saveInHistory(hello2).subscribe();
        repository.saveInHistory(hello3).subscribe();
        printHistory();
        repository.removeAllFromHistory().subscribe();
        printHistory();
        printAll();
        repository.removeAllFromFavourites().subscribe();
        printAll();
    }

    private Word buildWord(String word, String translation, boolean isFavourite){
        return Word.create(word, translation, "en", "ru", isFavourite);
    }

    private void printHistory(){
        repository.getHistory().subscribe(words -> {
            System.out.println("history");
            for (Word word : words) {
                System.out.println(word);
            }
        });
    }

    private void printFavourites(){
        repository.getHistory().subscribe(words -> {
            System.out.println("favourite");
            for (Word word : words) {
                System.out.println(word);
            }
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
