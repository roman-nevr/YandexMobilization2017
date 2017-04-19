package org.berendeev.roma.yandexmobilization2017.presentation.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.presentation.Utils;
import org.berendeev.roma.yandexmobilization2017.presentation.fragment.HistoryFragment;
import org.berendeev.roma.yandexmobilization2017.presentation.fragment.TranslatorFragment;
import org.berendeev.roma.yandexmobilization2017.presentation.view.WordListView;

public class MainActivity extends AppCompatActivity implements WordListView.Router{

    public static final String TRANSLATOR = "translator";
    public static final String FAVOURITES = "favourite";
    public static final String HISTORY = "history";

    public static final int TRANSLATOR_ID = 1;
    public static final int FAVOURITES_ID = 2;
    public static final int HISTORY_ID = 3;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private BottomNavigationView navigation;

    //Активити отвечает только за навигацию между фрагментами

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = navigation.getSelectedItemId();
            int from = navigation.getMenu().findItem(id).getOrder();

            switch (item.getItemId()) {
                case R.id.translator:
                    showFragment(TRANSLATOR, from, TRANSLATOR_ID);
                    return true;
                case R.id.favourite:
                    hideKeyboard();
                    showFragment(FAVOURITES, from, FAVOURITES_ID);
                    return true;
                case R.id.history:
                    hideKeyboard();
                    showFragment(HISTORY, from, HISTORY_ID);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null){
            beginTransaction();
            showFirstFragment(TRANSLATOR);
            commitTransaction();
        }

    }

    private void showFragment(String tag, int from, int to){
        if(from == to){
            return;
        }
        beginTransaction();
        setAnimation(from, to);
        showEnterFragment(tag);
        commitTransaction();
    }

    private void beginTransaction(){
        transaction = fragmentManager.beginTransaction();
    }

    private void commitTransaction(){
        transaction.commit();
    }

    private void setAnimation(int from, int to){
        if(from > to){
            transaction.setCustomAnimations(R.anim.to_left_in, R.anim.to_right_out);
        }else {
            transaction.setCustomAnimations(R.anim.to_right_in, R.anim.to_left_out);
        }
    }

    private void showEnterFragment(String tag) {
        transaction.replace(R.id.container, getFragment(tag), tag);
    }

    private void showFirstFragment(String tag){
        Fragment fragment = getFragment(tag);
        transaction.add(R.id.container, fragment, tag);

    }

    @NonNull private Fragment getFragment(String tag) {
        switch (tag){
            case TRANSLATOR:
                return new TranslatorFragment();
            case HISTORY:
                return HistoryFragment.getHistoryFragment();
            case FAVOURITES:
                return HistoryFragment.getFavouriteFragment();
            default:
                throw new IllegalArgumentException();
        }
    }

    private void hideKeyboard() {
        Utils.hideKeyboard(this, navigation);
    }

    @Override public void moveShowWordInTranslator() {
       navigation.setSelectedItemId(R.id.translator);
    }
}
