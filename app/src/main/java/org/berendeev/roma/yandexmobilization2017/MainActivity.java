package org.berendeev.roma.yandexmobilization2017;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.berendeev.roma.yandexmobilization2017.presentation.fragment.HistoryFragment;
import org.berendeev.roma.yandexmobilization2017.presentation.fragment.TranslatorFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TRANSLATOR = "translator";
    public static final String HISTORY = "history";
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.translator:
                    showTranslatorFragment();
                    return true;
                case R.id.favourite:
                    showHistoryFragment();
                    return true;
                case R.id.history:
                    showHistoryFragment();
                    return true;
            }
            return false;
        }

    };
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();

//        View actionBarView = getLayoutInflater().inflate(R.layout.translator_action_bar, null);

        ActionBar actionBar = getSupportActionBar();


    }

    private void showTranslatorFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction, HISTORY);

        Fragment fragment = fragmentManager.findFragmentByTag(TRANSLATOR);
        if(fragment == null){
            transaction.add(R.id.container, new TranslatorFragment(), TRANSLATOR).commit();
        }else {
            transaction.show(fragment).commit();
        }
    }

    private void showHistoryFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction, TRANSLATOR);

        Fragment fragment = fragmentManager.findFragmentByTag(HISTORY);
        if(fragment == null){
            transaction.add(R.id.container, new HistoryFragment(), HISTORY).commit();
        }else {
            transaction.show(fragment).commit();
        }
    }

    private void hideFragment(FragmentTransaction transaction, String tag){
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null && fragment.isVisible()){
            transaction.hide(fragment);
        }
    }

}
