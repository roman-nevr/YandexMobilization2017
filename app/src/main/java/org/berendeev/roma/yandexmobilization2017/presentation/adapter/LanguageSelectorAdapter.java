package org.berendeev.roma.yandexmobilization2017.presentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
import org.berendeev.roma.yandexmobilization2017.presentation.presenter.LanguageSelectorPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LanguageSelectorAdapter extends RecyclerView.Adapter<LanguageSelectorAdapter.LanguageHolder> {

    //адаптер для списка выбора языка

    private List<Map.Entry<String, String>> languages;
    private LanguageSelectorPresenter presenter;

    public LanguageSelectorAdapter(LanguageMap languageMap, LanguageSelectorPresenter presenter) {
        this.languages = new ArrayList<>(languageMap.map().entrySet());
        this.presenter = presenter;
        setHasStableIds(true);
    }

    @Override public long getItemId(int position) {
        return languages.get(position).getKey().hashCode() * languages.get(position).getValue().hashCode();
    }

    @Override public LanguageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.language_selector_item, parent, false);
        return new LanguageHolder(view);
    }

    @Override public void onBindViewHolder(LanguageHolder holder, int position) {
        holder.key = languages.get(position).getKey();
        holder.languageLabel.setText(languages.get(position).getValue());
    }

    @Override public int getItemCount() {
        return languages.size();
    }

    public class LanguageHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.languageLabel) TextView languageLabel;
        public String key;
        public LanguageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                presenter.onLanguageSelected(key);
            });
        }
    }
}
