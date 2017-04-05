package org.berendeev.roma.yandexmobilization2017.presentation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;


public class SoftEditText extends android.support.v7.widget.AppCompatEditText {

    private KeyImeChangeListener keyImeChangeListener;

    public SoftEditText(Context context) {
        super(context);
    }

    public SoftEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SoftEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if(keyImeChangeListener != null){
            keyImeChangeListener.onKeyPreIme(keyCode, event);
        }
        return super.onKeyPreIme(keyCode, event);

    }

    public void setKeyImeChangeListener(KeyImeChangeListener keyImeChangeListener) {
        this.keyImeChangeListener = keyImeChangeListener;
    }
}
