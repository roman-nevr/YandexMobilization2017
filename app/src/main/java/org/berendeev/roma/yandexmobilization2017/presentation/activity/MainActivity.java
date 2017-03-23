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
import org.berendeev.roma.yandexmobilization2017.presentation.fragment.FavouriteFragment;
import org.berendeev.roma.yandexmobilization2017.presentation.fragment.HistoryFragment;
import org.berendeev.roma.yandexmobilization2017.presentation.fragment.TranslatorFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String TRANSLATOR = "translator";
    public static final String HISTORY = "history";
    public static final String FAVOURITE = "favourite";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = navigation.getSelectedItemId();
            int from = navigation.getMenu().findItem(id).getOrder();

            switch (item.getItemId()) {
                case R.id.translator:
                    showFragment(TRANSLATOR, from, 1);
                    return true;
                case R.id.favourite:
                    showFragment(FAVOURITE, from, 2);
                    return true;
                case R.id.history:
                    showFragment(HISTORY, from, 3);
                    return true;
            }
            return false;
        }

    };
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null){
            beginTransaction();
            showEnterFragment(TRANSLATOR);
            commitTransaction();
        }

    }

    private void showFragment(String tag, int from, int to){
        if(from == to){
            return;
        }
        beginTransaction();
        setAnimation(from, to);
        hidePreviousFragment();
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
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null){
            transaction.show(fragment);
        }else {
            fragment = getFragment(tag);
            transaction.add(R.id.container, fragment, tag);
        }
    }

    @NonNull private Fragment getFragment(String tag) {
        switch (tag){
            case TRANSLATOR:
                return new TranslatorFragment();
            case HISTORY:
                return new HistoryFragment();
            case FAVOURITE:
                return new FavouriteFragment();
            default:
                throw new IllegalArgumentException();
        }
    }

    private void hideFragment(FragmentTransaction transaction, String tag){
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null && fragment.isVisible()){
            transaction.hide(fragment);
        }
    }

    private void hidePreviousFragment(){
        hideFragment(transaction, HISTORY);
        hideFragment(transaction, TRANSLATOR);
        hideFragment(transaction, FAVOURITE);
    }
}
