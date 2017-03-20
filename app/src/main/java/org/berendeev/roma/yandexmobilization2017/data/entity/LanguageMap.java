package org.berendeev.roma.yandexmobilization2017.data.entity;

public class LanguageMap {
    public String code;
    public String transcription;

    public LanguageMap(String code, String transcription) {
        this.code = code;
        this.transcription = transcription;
    }

    @Override public String toString() {
        return code + ":" + transcription;
    }
}
