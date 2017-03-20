package org.berendeev.roma.yandexmobilization2017.data.entity;

public class TranslateDirection {
    public String directionFrom;
    public String directionTo;

    public TranslateDirection(String directionFrom, String directionTo) {
        this.directionFrom = directionFrom;
        this.directionTo = directionTo;
    }

    @Override public String toString() {
        return directionFrom + ":" + directionTo;
    }
}
