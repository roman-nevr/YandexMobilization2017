package org.berendeev.roma.yandexmobilization2017.presentation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
import org.berendeev.roma.yandexmobilization2017.presentation.App;
import org.berendeev.roma.yandexmobilization2017.presentation.adapter.LanguageSelectorAdapter;
import org.berendeev.roma.yandexmobilization2017.presentation.presenter.LanguageSelectorPresenter;
import org.berendeev.roma.yandexmobilization2017.presentation.view.LanguageSelectorView;
import org.berendeev.roma.yandexmobilization2017.presentation.view.LanguageSelectorView.Router;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LanguageSelectorActivity extends AppCompatActivity implements LanguageSelectorView, Router {

    public static final String TYPE = "type";
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Inject LanguageSelectorPresenter presenter;

    private LanguageSelectorAdapter adapter;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initDI();
    }

    private void initDI() {
        App.getApplication().getMainComponent().plusLanguageSelectorComponent().inject(this);
    }

    @Override protected void onStart() {
        super.onStart();
        presenter.setType(getType());
        presenter.setView(this);
        presenter.setRouter(this);
        presenter.start();
    }

    @Override protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    private void initUI() {
        setContentView(R.layout.language_selector);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.title_favourite);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override public void showLanguages(LanguageMap map) {
        adapter = new LanguageSelectorAdapter(map, presenter);
        recyclerView.setAdapter(adapter);
    }

    @Override public void setTitleById(int titleId) {
        setTitle(titleId);
    }

    public static void start(Context context, int type){
        Intent intent = new Intent(context, LanguageSelectorActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }

    private int getType(){
        return getIntent().getIntExtra(TYPE, -1);
    }

    @Override public void moveToTranslator() {
        finish();
    }
}
