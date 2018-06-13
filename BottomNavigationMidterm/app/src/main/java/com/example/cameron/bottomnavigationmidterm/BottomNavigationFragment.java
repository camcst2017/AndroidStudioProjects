package com.example.cameron.bottomnavigationmidterm;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class BottomNavigationFragment extends Fragment {

        public static final String ARG_TITLE = "arg_title";
        public static final String ARG_COLOR = "arg_color";


    @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_bottom_navigation, container, false);
            TextView textView = rootView.findViewById(R.id.fragment_text);
            FrameLayout frameLayout = rootView.findViewById(R.id.fragFrameLayout);

            String title = null;
            int color = Color.WHITE;
            if (getArguments() != null) {
                title = getArguments().getString(ARG_TITLE, "");
                color = getArguments().getInt(ARG_COLOR, -1);
            }

            frameLayout.setBackgroundColor(color);
            textView.setText(title);
            return rootView;
        }

    }
