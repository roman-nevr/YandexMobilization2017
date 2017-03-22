package org.berendeev.roma.yandexmobilization2017.presentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import org.berendeev.roma.yandexmobilization2017.R;

import butterknife.BindView;


public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordHolder> {

    @Override public WordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override public void onBindViewHolder(WordHolder holder, int position) {

    }

    @Override public int getItemCount() {
        return 0;
    }

    public class WordHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.fav_button) ImageButton favButton;

        public WordHolder(View itemView) {
            super(itemView);
        }
    }
}
