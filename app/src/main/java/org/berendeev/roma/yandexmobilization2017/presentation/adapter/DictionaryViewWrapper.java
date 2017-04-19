package org.berendeev.roma.yandexmobilization2017.presentation.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import org.berendeev.roma.yandexmobilization2017.domain.entity.Definition;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;

import java.util.List;

public class DictionaryViewWrapper {

    //Это маппер словаря для отображения в TextView

    private int textPixelSize;
    private int headerPixelSize;
    private int speechPartColor, textColor;
    private int exactly;

    public DictionaryViewWrapper(int speechPartColor, int textColor, int headerPixelSize, int textPixelSize) {
        this.speechPartColor = speechPartColor;
        this.textColor = textColor;
        this.headerPixelSize = headerPixelSize;
        this.textPixelSize = textPixelSize;
        exactly = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
    }

    public SpannableString getText(Dictionary dictionary){
        SpannableStringBuilder builder = new SpannableStringBuilder();
        spanHeader(builder, dictionary.text(), dictionary.transcription());
        spanDefinitions(builder, dictionary.definitions());
        return new SpannableString(builder);
    }

    private void spanHeader(SpannableStringBuilder builder, String text, String transcription) {
        builder.append(text);
        int length = text.length();
        if(!transcription.isEmpty()){
            builder.append(" [" + transcription + "]");
            builder.setSpan(new ForegroundColorSpan(Color.GRAY),
                    builder.length() - transcription.length() - 2, builder.length(),
                    exactly);
            length = length + transcription.length() + 3;
        }
        builder.setSpan(new AbsoluteSizeSpan(headerPixelSize), builder.length() - length, builder.length(), exactly);
        builder.append("\n");

    }

    private void spanDefinitions(SpannableStringBuilder builder, List<Definition> definitions) {
        for (Definition definition : definitions) {
            spanDefinition(builder, definition);
        }
    }

    private void spanDefinition(SpannableStringBuilder builder, Definition definition) {
        builder.append(definition.speechPart());
        builder.setSpan(new ForegroundColorSpan(speechPartColor),
                builder.length() - definition.speechPart().length(), builder.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(" ");
        builder.setSpan(new RelativeSizeSpan(1.5f), builder.length() - 1, builder.length(), exactly);
        builder.append("\n");
        builder.setSpan(new RelativeSizeSpan(1.5f), builder.length() - 1, builder.length(), exactly);

        for (int number = 0; number < definition.translations().size(); number++) {
            spanTranslation(builder, definition.translations().get(number), number);
        }
    }

    private void spanTranslation(SpannableStringBuilder builder, String translation, int number) {
        String numberString = "" + (number + 1) + " ";
        builder.append(numberString);
        builder.setSpan(new RelativeSizeSpan(0.9f), builder.length() - numberString.length(), builder.length(), exactly);
        builder.append(translation);
        builder.setSpan(new ForegroundColorSpan(textColor),
                builder.length() - translation.length(), builder.length(), exactly);
        builder.setSpan(new AbsoluteSizeSpan(textPixelSize),
                builder.length() - translation.length(), builder.length(), exactly);
        builder.append("\n");
    }
}
