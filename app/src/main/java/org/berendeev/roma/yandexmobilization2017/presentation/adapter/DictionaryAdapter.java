package org.berendeev.roma.yandexmobilization2017.presentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.berendeev.roma.yandexmobilization2017.BuildConfig;
import org.berendeev.roma.yandexmobilization2017.R;
import org.berendeev.roma.yandexmobilization2017.data.entity.Translation;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Definition;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;

import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DictionaryAdapter extends RecyclerView.Adapter {

    private static final int HEAD = 0;
    private static final int SPEECH_PART = 1;
    private static final int TRANSLATION = 2;

    private Dictionary dictionary;

    public DictionaryAdapter(Dictionary dictionary) {
        this.dictionary = dictionary;
        setHasStableIds(true);
    }

    @Override public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    private Object getItem(int position) {
        if (position == 0) {
            return dictionary.text();
        } else {
            position--;
        }
        for (Definition definition : dictionary.definitions()) {
            if (position <= definition.translations().size()) {
                if (position == 0) {
                    return definition.speechPart();
                } else {
                    position--;
                    return definition.translations().get(position);
                }
            } else {
                position = position - definition.translations().size() - 1;
            }
        }
        return null;//it can't be
    }

    @Override public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD;
        } else {
            position--;
        }
        for (Definition definition : dictionary.definitions()) {
            if (position <= definition.translations().size()) {
                if (position == 0) {
                    return SPEECH_PART;
                } else {
                    position--;
                    return TRANSLATION;
                }
            } else {
                position = position - definition.translations().size() - 1;
            }
        }
        if(BuildConfig.DEBUG){
            throw new IllegalArgumentException("wrong view type");
        }
        return -1;//it can't be
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEAD:
                return createTranscriptionHolder(parent);
            case SPEECH_PART:
                return createSpeechPartHolder(parent);
            case TRANSLATION:
                return createTranslationHolder(parent);
            default:
                return null;
        }
    }

    private RecyclerView.ViewHolder createTranslationHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.translation, parent, false);
        return new TranslationHolder(view);
    }

    private SpeechPartHolder createSpeechPartHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.speech_part, parent, false);
        return new SpeechPartHolder(view);
    }

    private TranscriptionHolder createTranscriptionHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transcription, parent, false);
        return new TranscriptionHolder(view);
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TranscriptionHolder) {
            bindTranscriptionHolder(position, (TranscriptionHolder) holder);
        }
        if (holder instanceof SpeechPartHolder) {
            bindSpeechPartHolder(position, (SpeechPartHolder) holder);
        }
        if (holder instanceof TranslationHolder) {
            bindTranslationHolder(position, (TranslationHolder) holder);
        }
    }

    private void bindTranslationHolder(int position, TranslationHolder holder) {
        int index = findPositionNumber(position);
        holder.number.setText("" + index);
        holder.translation.setText(getItem(position).toString());
    }

    private int findPositionNumber(int position){
        position--;
        for (Definition definition : dictionary.definitions()) {
            if (position <= definition.translations().size()) {
                return position;
            } else {
                position = position - definition.translations().size() - 1;
            }
        }
        throw new IllegalArgumentException();
    }

    private void bindSpeechPartHolder(int position, SpeechPartHolder holder) {
        String speechPart = (String) getItem(position);
        holder.speechPart.setText(speechPart);
    }

    private void bindTranscriptionHolder(int position, TranscriptionHolder holder) {
        holder.text.setText(dictionary.text());
        if(!dictionary.transcription().isEmpty()){
            holder.transcription.setText("[" + dictionary.transcription() + "]");
        }else {
            holder.transcription.setText("");
        }
    }

    @Override public int getItemCount() {
        if(dictionary.text().equals("")){
            return 0;
        }
        int count = 1; //есть транскрипция, may be
        for (Definition definition : dictionary.definitions()) {
            count = count + definition.translations().size() + 1;//есть часть речи
        }
        return count;
    }

    public void changeItems(Dictionary dictionary) {
        this.dictionary = dictionary;
        notifyDataSetChanged();
    }

    public class TranscriptionHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text) TextView text;
        @BindView(R.id.transcription) TextView transcription;

        public TranscriptionHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class SpeechPartHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.speech_part) TextView speechPart;

        public SpeechPartHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class TranslationHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.number) TextView number;
        @BindView(R.id.translation) TextView translation;

        public TranslationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
