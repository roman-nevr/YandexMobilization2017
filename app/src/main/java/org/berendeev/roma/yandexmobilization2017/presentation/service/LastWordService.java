package org.berendeev.roma.yandexmobilization2017.presentation.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;


public class LastWordService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public LastWordService() {
        super("name");
    }

    @Override protected void onHandleIntent(@Nullable Intent intent) {
        //TODO saveLastWord
    }
}
