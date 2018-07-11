package com.example.cameron.examplebottomnavigation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<BottomNavigationFragment> fragmentList = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(0);
                    return true;
                case R.id.navigation_dashboard:
                    switchFragment(1);
                    return true;
                case R.id.navigation_notifications:
                    switchFragment(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        populateFragmentList();
        switchFragment(0);
    }

    private BottomNavigationFragment createFragment(String title, int color) {

        BottomNavigationFragment fragment = new BottomNavigationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BottomNavigationFragment.ARG_TITLE, title);
        bundle.putInt(BottomNavigationFragment.ARG_COLOR, color);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void populateFragmentList() {

        BottomNavigationFragment homeFragment = createFragment("Home", Color.RED);
        BottomNavigationFragment dashboardFragment = createFragment("Dashboard", Color.GREEN);
        BottomNavigationFragment notificationsFragment = createFragment("Notifications", Color.BLUE);

        fragmentList.add(homeFragment);
        fragmentList.add(dashboardFragment);
        fragmentList.add(notificationsFragment);
    }

    private void switchFragment(int pos) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, fragmentList.get(pos))
                .commit();
    }

}
