package com.example.dtttestapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class InfoFragment extends Fragment {

    private TextView hyperlink;
    public InfoFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        hyperlink = view.findViewById(R.id.url);
        hyperlink.setMovementMethod(LinkMovementMethod.getInstance());
        hyperlink.setLinkTextColor(Color.BLUE);

        return view;
    }
}
