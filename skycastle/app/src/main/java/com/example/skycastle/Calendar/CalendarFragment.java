package com.example.skycastle.Calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.CalendarView;
import com.example.skycastle.R;
import com.example.skycastle.SettingFragment;
import com.example.skycastle.MainActivity;

public class CalendarFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_calendar, container, false);
        setHasOptionsMenu(true);

        CalendarView calendarView = (CalendarView)rootView.findViewById(R.id.calendarView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fg_calendar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_calendar_setting:
                Toast.makeText(getActivity(), "menu_calendar_setting", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).replaceFragment(new SettingFragment());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
