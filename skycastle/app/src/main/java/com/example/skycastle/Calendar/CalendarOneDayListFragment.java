package com.example.skycastle.Calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.EventDay;
import com.example.skycastle.R;

import java.util.Map;

public class CalendarOneDayListFragment extends DialogFragment {
    String time;
    DayClass now_map;

    public CalendarOneDayListFragment(String time, DayClass now_map){
        this.time=time;
        this.now_map=now_map;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar_oneday, container, false);

        return rootView;
    }
}
