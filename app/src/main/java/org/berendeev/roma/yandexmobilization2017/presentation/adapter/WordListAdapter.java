package org.berendeev.roma.yandexmobilization2017.presentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Word;
import org.berendeev.roma.yandexmobilization2017.presentation.Utils;
import org.berendeev.roma.yandexmobilization2017.presentation.presenter.HistoryPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordHolder> {

    private List<Word> words;
    private HistoryPresenter presenter;
    private int colorFavourite;
    private int colorNotFavourite;

    public WordListAdapter(List<Word> words, HistoryPresenter presenter, int colorFavourite, int colorNotFavourite) {
        this.words = words;
        this.presenter = presenter;
        this.colorFavourite = colorFavourite;
        this.colorNotFavourite = colorNotFavourite;
        setHasStableIds(true);
    }

    @Override public long getItemId(int position) {
        return words.get(position).hashCode();
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
        holder.favButton.setColorFilter(word.isFavourite() ? colorFavourite : colorNotFavourite);
    }

    @Override public int getItemCount() {
        return words.size();
    }

    public void updateList(List<Word> words){
        this.words = words;
        notifyDataSetChanged();
    }

    public void setIsItemFavourite(int position, boolean isFavourite){
        words.set(position, words.get(position).toBuilder().isFavourite(isFavourite).build());
        notifyItemChanged(position);
    }

    public class WordHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.fav_button) ImageButton favButton;
        @BindView(R.id.word_to_translate) TextView tvText;
        @BindView(R.id.translation) TextView translation;
        @BindView(R.id.direction) TextView direction;

        public Word word;

        public WordHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            favButton.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION){
                    presenter.onFavButtonClick(getAdapterPosition());
                }
            });

            itemView.setOnClickListener(v -> {
                presenter.onItemClick(getAdapterPosition());
            });
        }

        public void switchOnFavButton() {
            Utils.animateImageButtonColor(favButton, colorNotFavourite, colorFavourite);
        }

        public void switchOffFavButton() {
            Utils.animateImageButtonColor(favButton, colorFavourite, colorNotFavourite);
        }
    }
}
