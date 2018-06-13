package com.example.cameron.bottomnavigationmidterm;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BottomNavigationFragment extends Fragment {

        public static final String ARG_TITLE = "arg_title";


    @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_bottom_navigation, container, false);
            TextView textView = rootView.findViewById(R.id.fragment_text);

            String title = null;
            if (getArguments() != null) {
                title = getArguments().getString(ARG_TITLE, "");
            }
            textView.setText(title);
            return rootView;
        }

    }
