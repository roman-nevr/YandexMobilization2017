package org.berendeev.roma.yandexmobilization2017.presentation;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageButton;

import org.berendeev.roma.yandexmobilization2017.R;

public class ImageButtonColorSwitcher implements View.OnClickListener{

    private boolean isActive;
    private int colorFavourite;
    private int colorNotFavourite;

    public ImageButtonColorSwitcher(boolean isActive, int colorFavourite, int colorNotFavourite) {
        this.isActive = isActive;
        this.colorFavourite = colorFavourite;
        this.colorNotFavourite = colorNotFavourite;
    }

    @Override public void onClick(View v) {
        int colorFrom, colorTo;

        if(isActive){
            colorFrom = colorFavourite;
            colorTo = colorNotFavourite;
        }else {
            colorFrom = colorNotFavourite;
            colorTo = colorFavourite;
        }
        isActive = !isActive;
        ObjectAnimator animator = ObjectAnimator.ofObject(((ImageButton) v),
                "colorFilter", new ArgbEvaluator(), colorFrom, colorTo);

        animator.setDuration(500);
        animator.start();
    }
}
