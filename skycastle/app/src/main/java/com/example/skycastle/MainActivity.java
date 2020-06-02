package com.example.skycastle;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.skycastle.R;
import com.example.skycastle.Calendar.CalendarFragment;
import com.example.skycastle.Home.HomeFragment;
import com.example.skycastle.UnivReveiw.ReviewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private MenuItem menuItem;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private HomeFragment homeFragment = new HomeFragment();
    private CalendarFragment calendarFragment = new CalendarFragment();
    private ReviewFragment reviewFragment = new ReviewFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Init View setting
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, homeFragment).commitAllowingStateLoss();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }

    private class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.menu_home:
                    fragmentTransaction.replace(R.id.main_container, homeFragment).commitAllowingStateLoss();
                    break;
                case R.id.menu_calendar:
                    fragmentTransaction.replace(R.id.main_container, calendarFragment).commitAllowingStateLoss();
                    break;
                case R.id.menu_review:
                    fragmentTransaction.replace(R.id.main_container, reviewFragment).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment).addToBackStack(null).commit();
    }
}
