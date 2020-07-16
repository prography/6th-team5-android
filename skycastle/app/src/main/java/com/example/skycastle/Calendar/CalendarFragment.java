package com.example.skycastle.Calendar;

import android.os.Bundle;
import android.util.Log;
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
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.skycastle.Home.HomeAdapter;
import com.example.skycastle.Home.HomeItem;
import com.example.skycastle.R;
import com.example.skycastle.ServerData.ServerData;
import com.example.skycastle.ServerData.schdules;
import com.example.skycastle.SettingFragment;
import com.example.skycastle.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarFragment extends Fragment {
    private Fragment fragment;
    List<ServerData> univData = new ArrayList<ServerData>();
    //Calendar calendar;
    public CalendarFragment(List<ServerData> univData,Map<Calendar,List<DayClass>> eventMap, List<EventDay> events){
        this.univData=univData;
        this.eventMap=eventMap;
        this.events=events;
    }
    //int dYear, dMonth, dDay;
    //EventDay ed;
    Map<Calendar,List<DayClass>> eventMap = new HashMap<>();
    //DayClass dc=new DayClass();
    //List<DayClass> dc_list=new ArrayList<DayClass>();
    List<EventDay> events = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_calendar, container, false);
        setHasOptionsMenu(true);

        CalendarView calendarView = (CalendarView)rootView.findViewById(R.id.calendarView);

        // 달력 보여주는 범위 정하기
        Calendar min = Calendar.getInstance();
        min.add(Calendar.DAY_OF_MONTH, -365);
        Calendar max = Calendar.getInstance();
        max.add(Calendar.DAY_OF_MONTH, 365);
        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(@NotNull EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                /*Toast.makeText(getContext(),
                        eventDay.getCalendar().getTime().toString() + " "
                                + eventDay.isEnabled(),
                        Toast.LENGTH_SHORT).show();*/
                List<DayClass> now_dc= eventMap.get(eventDay.getCalendar());
                CalendarOneDayListFragment dialogFragment = new CalendarOneDayListFragment(eventDay.getCalendar().getTime().toString(),now_dc,eventDay.getCalendar().getTime().toString());
                dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog");
            }
        });

        calendarView.setEvents(events);

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
                ((BaseActivity)getActivity()).replaceFragment(new SettingFragment());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
