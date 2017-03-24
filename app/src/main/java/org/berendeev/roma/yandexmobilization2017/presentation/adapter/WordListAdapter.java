package org.berendeev.roma.yandexmobilization2017.presentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.presentation.ImageButtonColorSwitcher;
import org.berendeev.roma.yandexmobilization2017.presentation.presenter.HistoryPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordHolder> {

    private List<Word> words;
    private HistoryPresenter presenter;

    public WordListAdapter(List<Word> words, HistoryPresenter presenter) {
        this.words = words;
        this.presenter = presenter;
    }

    @Override public WordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new WordHolder(view);
    }

    @Override public void onBindViewHolder(WordHolder holder, int position) {
        Word word = words.get(position);
        holder.tvText.setText(word.word());
        holder.translation.setText(word.translation());
        holder.direction.setText(word.languageFrom() + "-" + word.languageTo());
    }

    @Override public int getItemCount() {
        return words.size();
    }

    public class WordHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.fav_button) ImageButton favButton;
        @BindView(R.id.word_to_translate) TextView tvText;
        @BindView(R.id.translation) TextView translation;
        @BindView(R.id.direction) TextView direction;

        public WordHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            favButton.setOnClickListener(v -> {

            });

            itemView.setOnClickListener(v -> {

            });

        }
    }
}
