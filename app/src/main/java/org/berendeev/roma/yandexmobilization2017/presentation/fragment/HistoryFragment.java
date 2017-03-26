package org.berendeev.roma.yandexmobilization2017.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.presentation.App;
import org.berendeev.roma.yandexmobilization2017.presentation.adapter.WordListAdapter;
import org.berendeev.roma.yandexmobilization2017.presentation.presenter.HistoryPresenter;
import org.berendeev.roma.yandexmobilization2017.presentation.view.WordListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HistoryFragment extends Fragment implements WordListView {

    public static final String TYPE = "type";
    @Inject HistoryPresenter presenter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.delete_all_button) ImageButton deleteButton;

    private WordListAdapter adapter;
    private int colorFavourite;
    private int colorNotFavourite;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_layout, container, false);
        initDi();
        initUi(view);
        return view;
    }

    @Override public void onStart() {
        super.onStart();
        int type = getArguments().getInt(TYPE);
        presenter.setType(type);
        presenter.setView(this);
        presenter.start();
    }

    @Override public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            presenter.start();
        }
    }

    private void initDi() {
        App.getApplication().getMainComponent().plusHistoryComponent().inject(this);
    }

    private void initUi(View view) {
        ButterKnife.bind(this, view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        deleteButton.setOnClickListener(v -> {
            presenter.deleteAll();
        });
        colorFavourite = getResources().getColor(R.color.colorPrimary);
        colorNotFavourite = getResources().getColor(R.color.grey);
    }

    @Override public void showList(List<Word> wordList) {
        adapter = new WordListAdapter(wordList, presenter, colorFavourite, colorNotFavourite);
        recyclerView.setAdapter(adapter);
    }

    @Override public void setTitleById(@StringRes int id) {
        title.setText(id);
    }

    @Override public void switchOffFavButtonAt(int index) {
        ((WordListAdapter.WordHolder)recyclerView.getChildViewHolder(recyclerView.getChildAt(index))).switchOffFavButton();

    }

    @Override public void switchOnFavButtonAt(int index) {
        ((WordListAdapter.WordHolder)recyclerView.getChildViewHolder(recyclerView.getChildAt(index))).switchOnFavButton();
    }

    public static Fragment getHistoryFragment(){
        return getInstance(R.id.history_type);
    }

    public static Fragment getFavouriteFragment(){
        return getInstance(R.id.favourites_type);
    }

    private static Fragment getInstance(int type){
        Fragment fragment = new HistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }
}
