package org.berendeev.roma.yandexmobilization2017.presentation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.berendeev.roma.yandexmobilization2017.BuildConfig;
import org.berendeev.roma.yandexmobilization2017.R;


public class DeleteAllDialog extends DialogFragment {

    private DeleteAllDialogListener listener;
    private int type;

    public static final String TYPE = "type";

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DeleteDialog.DeleteDialogListener){
            this.listener = (DeleteAllDialogListener)context;
        }
        if (getParentFragment() instanceof DeleteDialog.DeleteDialogListener){
            this.listener = (DeleteAllDialogListener)getParentFragment();
        }

        if (listener == null){
            throw new IllegalArgumentException("context must implement DeleteDialogListener");
        }
    }

    @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        readType();
        View form = createUIView();
        return buildDialog(form);
    }

    private Dialog buildDialog(View form){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(form);
        Dialog dialog = builder.create();
        return dialog;
    }

    private void readType(){
        Bundle bundle = getArguments();
        type = bundle.getInt(TYPE, -1);
        if (type == -1 && BuildConfig.DEBUG){
            throw new IllegalArgumentException("type must not be -1");
        }
    }

    private View createUIView(){
        View form = getActivity().getLayoutInflater()
                .inflate(R.layout.delete_all_dialog, null, true);
        TextView addition = (TextView) form.findViewById(R.id.addition);
        if(type == R.id.favourites_type){
            addition.setText(R.string.delete_all_favourites);
        }
        if(type == R.id.history_type){
            addition.setText(R.string.delete_all_history);
        }
        Button btnDelete = (Button) form.findViewById(R.id.delete);
        Button btnCancel = (Button) form.findViewById(R.id.cancel);
        btnDelete.setOnClickListener(v -> {
            listener.onDeleteAllConfirm();
            dismiss();
        });
        btnCancel.setOnClickListener(v -> dismiss());
        return form;
    }

    public static DialogFragment getInstance(int type){
        DeleteAllDialog fragment = new DeleteAllDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public interface DeleteAllDialogListener{
        public void onDeleteAllConfirm();
    }
}
