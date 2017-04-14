package org.berendeev.roma.yandexmobilization2017.presentation.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import org.berendeev.roma.yandexmobilization2017.domain.entity.Definition;
import org.berendeev.roma.yandexmobilization2017.domain.entity.Dictionary;

import java.util.List;

public class DictionaryViewMapper {

    public static SpannableString map(Dictionary dictionary){
        SpannableStringBuilder builder = new SpannableStringBuilder();
        spanHeader(builder, dictionary.text(), dictionary.transcription());
        spanDefinitions(builder, dictionary.definitions());
        return new SpannableString(builder);
    }

    private static void spanHeader(SpannableStringBuilder builder, String text, String transcription) {
        builder.append(text);
        builder.append(" [" + transcription + "]");
        builder.setSpan(new ForegroundColorSpan(Color.GRAY),
                builder.length() - transcription.length() - 2, builder.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append("\n");
    }

    private static void spanDefinitions(SpannableStringBuilder builder, List<Definition> definitions) {
        for (Definition definition : definitions) {
            spanDefinition(builder, definition);
        }
    }

    private static void spanDefinition(SpannableStringBuilder builder, Definition definition) {
        builder.append(definition.speechPart());
        builder.setSpan(new ForegroundColorSpan(Color.MAGENTA),
                builder.length() - definition.speechPart().length(), builder.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append("\n");

        for (String translation : definition.translations()) {
            spanTranslation(builder, translation);
        }
    }

    private static void spanTranslation(SpannableStringBuilder builder, String translation) {
        builder.append(translation);
        builder.setSpan(new ForegroundColorSpan(Color.BLUE),
                builder.length() - translation.length(), builder.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append("\n");
    }
}
