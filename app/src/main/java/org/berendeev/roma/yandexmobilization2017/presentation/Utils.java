package org.berendeev.roma.yandexmobilization2017.presentation;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

public class Utils {

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void animateImageButtonColor(ImageButton button, int colorFrom, int colorTo) {
        ObjectAnimator animator = ObjectAnimator.ofObject(button,
                "colorFilter", new ArgbEvaluator(), colorFrom, colorTo);

        animator.setDuration(500);
        animator.start();
    }
}
