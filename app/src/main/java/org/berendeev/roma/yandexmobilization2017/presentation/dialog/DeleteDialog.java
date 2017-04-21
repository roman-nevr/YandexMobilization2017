package org.berendeev.roma.yandexmobilization2017.presentation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import org.berendeev.roma.yandexmobilization2017.BuildConfig;
import org.berendeev.roma.yandexmobilization2017.R;


public class DeleteDialog extends DialogFragment {

    private static final String NUMBER = "number";
    private DeleteDialogListener listener;
    private int number;

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DeleteDialogListener){
            this.listener = (DeleteDialogListener)context;
        }
        if (getParentFragment() instanceof DeleteDialogListener){
            this.listener = (DeleteDialogListener)getParentFragment();
        }

        if (listener == null){
            throw new IllegalArgumentException("context must implement DeleteDialogListener");
        }
    }

    @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        readNumber();
        View form = createUIView();
        return buildDialog(form);
    }

    private View createUIView(){
        View form = getActivity().getLayoutInflater()
                .inflate(R.layout.delete_item_dialog, null, true);
        TextView delete = (TextView) form.findViewById(R.id.delete);
        delete.setOnClickListener(v -> {
            listener.onDialogComplete(number);
            dismiss();
        });
        return form;
    }

    private Dialog buildDialog(View form){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(form);
        Dialog dialog = builder.create();
        return dialog;
    }

    private void readNumber(){
        Bundle bundle = getArguments();
        number = bundle.getInt(NUMBER, -1);
        if (number == -1 && BuildConfig.DEBUG){
            throw new IllegalArgumentException("number must be real");
        }
    }

    public static DialogFragment getInstance(int categoryId){
        DeleteDialog fragment = new DeleteDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(NUMBER, categoryId);
        fragment.setArguments(bundle);
        return fragment;
    }

    public interface DeleteDialogListener{
        void onDialogComplete(int number);
    }
}
