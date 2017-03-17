package org.berendeev.roma.yandexmobilization2017.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.berendeev.roma.yandexmobilization2017.R;


public class FavouriteFragment extends Fragment {
    private Toolbar toolbar;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_layout, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(R.string.title_favourite);
        return view;
    }
}
