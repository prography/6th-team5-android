package com.output.ipsi.Calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.output.ipsi.R;

import java.util.List;

public class CalendarOneDayListFragment extends DialogFragment {
    String time;
    List<DayClass> now_map;
    String date;

    public CalendarOneDayListFragment(String time, List<DayClass> now_map, String date){
        this.time=time;
        this.now_map=now_map;
        this.date=date;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar_oneday, container, false);
        TextView d_t=rootView.findViewById(R.id.date_t);
        TextView d_t2=rootView.findViewById(R.id.date_t2);

        String[] targetSlicing2 = date.split(" ");
        String setdate=targetSlicing2[1]+" "+targetSlicing2[2];
        d_t.setText(setdate);
        d_t2.setText(targetSlicing2[0]);

        if(now_map!=null){
            RecyclerView mRecyclerView = rootView.findViewById(R.id.calendar_recycler);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            CalendarRecyclerAdapter list_adapter = new CalendarRecyclerAdapter(getContext(), now_map);
            mRecyclerView.setAdapter(list_adapter);
        }

        return rootView;
    }
}
