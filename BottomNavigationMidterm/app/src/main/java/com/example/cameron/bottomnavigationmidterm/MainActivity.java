package com.example.cameron.bottomnavigationmidterm;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_HOME_FRAG = "TAG_HOME_FRAG";
    private static final String TAG_DASHBOARD_FRAG= "TAG_DASHBOARD_FRAG";
    private static final String TAG_NOTIFICATIONS_FRAG = "TAG_NOTIFICATIONS_FRAG";

    FrameLayout frameLayout;

    private List<BottomNavigationFragment> fragments = new ArrayList<>(3);

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(0, TAG_HOME_FRAG);
                    return true;
                case R.id.navigation_dashboard:
                    switchFragment(1, TAG_DASHBOARD_FRAG);
                    return true;
                case R.id.navigation_notifications:
                    switchFragment(2, TAG_NOTIFICATIONS_FRAG);
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

        frameLayout = findViewById(R.id.frame);

        buildFragmentsList();
        switchFragment(0, TAG_HOME_FRAG);
    }

    private void buildFragmentsList() {
        BottomNavigationFragment homeFragment = buildFragment("Home", Color.RED);
        BottomNavigationFragment dashboardFragment = buildFragment("Dashboard", Color.GREEN);
        BottomNavigationFragment notificationsFragment = buildFragment("Notifications", Color.BLUE);

        fragments.add(homeFragment);
        fragments.add(dashboardFragment);
        fragments.add(notificationsFragment);
    }

    private BottomNavigationFragment buildFragment(String title, int color) {
        BottomNavigationFragment fragment = new BottomNavigationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BottomNavigationFragment.ARG_TITLE, title);
        bundle.putInt(BottomNavigationFragment.ARG_COLOR, color);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void switchFragment(int pos, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, fragments.get(pos), tag)
                .commit();
    }
}
