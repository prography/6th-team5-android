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
    public CalendarFragment(List<ServerData> univData){
        this.univData=univData;
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

        for(int i=0;i<univData.size();i++){
            for(int j=0;j<univData.get(i).getSjs().size();j++){
                for(int k=0;k<univData.get(i).getSjs().get(j).getJhs().size();k++){
                    for(int l=0;l<univData.get(i).getSjs().get(j).getJhs().get(k).getMajors().size();l++){
                        int check=0;
                        for(int m=0;m<univData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getSchedules().size();m++){
                            schdules scd=univData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getSchedules().get(m);
                            //start_date
                            if(scd.getStart_date()!=null){
                            String[] targetSlicing = scd.getStart_date().split("-");
                            int dYear = Integer.parseInt(targetSlicing[0]);
                            int dMonth = Integer.parseInt(targetSlicing[1]);
                            int dDay = Integer.parseInt(targetSlicing[2]);
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(dYear, dMonth-1, dDay);
                            EventDay ed=new EventDay(calendar, R.drawable.cal_icon);
                            DayClass dc=new DayClass();
                                String t1=univData.get(i).getName()+" "
                                        +univData.get(i).getSjs().get(j).getSj()+" "
                                        +univData.get(i).getSjs().get(j).getJhs().get(k).getName()
                                        +univData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getName();
                                dc.setUniv_info(t1);
                                dc.setLogo(univData.get(i).getLogo());
                                dc.setDescription(scd.getDescription());
                                dc.setHour(targetSlicing[3]);
                                dc.setMinute(targetSlicing[4]);
                                dc.setS_or_e(0);
                            if(eventMap.get(calendar)==null){
                                List<DayClass> dc_list=new ArrayList<DayClass>();
                                dc_list.add(dc);
                                eventMap.put(calendar,dc_list);
                                events.add(ed);
                            }else{
                                eventMap.get(calendar).add(dc);
                                //events.add(ed);
                            }
                           }

                            //end_date
                            Log.d("end_Date",scd.getEnd_date());
                            if(scd.getEnd_date()!=null) {
                                String[] targetSlicing2 = scd.getEnd_date().split("-");
                                int dYear = Integer.parseInt(targetSlicing2[0]);
                                int dMonth = Integer.parseInt(targetSlicing2[1]);
                                int dDay = Integer.parseInt(targetSlicing2[2]);
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(dYear, dMonth - 1, dDay);
                                EventDay ed = new EventDay(calendar, R.drawable.cal_icon);

                                String t2 = univData.get(i).getName() + " "
                                        + univData.get(i).getSjs().get(j).getSj() + " "
                                        + univData.get(i).getSjs().get(j).getJhs().get(k).getName() + " "
                                        + univData.get(i).getSjs().get(j).getJhs().get(k).getMajors().get(l).getName();
                                DayClass dc=new DayClass();
                                dc.setUniv_info(t2);
                                dc.setLogo(univData.get(i).getLogo());
                                dc.setDescription(scd.getDescription());
                                dc.setHour(targetSlicing2[3]);
                                dc.setMinute(targetSlicing2[4]);
                                dc.setS_or_e(1);

                                if(eventMap.get(calendar)==null){
                                    List<DayClass> dc_list=new ArrayList<DayClass>();
                                    dc_list.add(dc);
                                    eventMap.put(calendar,dc_list);
                                    events.add(ed);
                                }else{
                                    eventMap.get(calendar).add(dc);
                                    //events.add(ed);
                                }//시작일이랑 끝이랑 아이콘 다르게
                            }
                        }
                    }
                }
            }
        }

        Calendar calendar = Calendar.getInstance();
        EventDay ed=new EventDay(calendar, R.drawable.cal_icon);
        if(eventMap.get(calendar)==null){
            events.add(ed);
        }

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
                Toast.makeText(getContext(),
                        eventDay.getCalendar().getTime().toString() + " "
                                + eventDay.isEnabled(),
                        Toast.LENGTH_SHORT).show();
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
