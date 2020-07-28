package com.output.ipsi.Calendar;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.output.ipsi.R;

import java.util.ArrayList;
import java.util.List;

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.ItemViewHolder>{
    private List<DayClass> selectedData=new ArrayList<DayClass>();
    private Context context;
    private View view;

    public CalendarRecyclerAdapter(Context context,List<DayClass> data){
        this.context=context;
        selectedData=data;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public CalendarRecyclerAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_oneday_recycler, parent, false);

        return new CalendarRecyclerAdapter.ItemViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull CalendarRecyclerAdapter.ItemViewHolder holder, int position) {
        holder.onBind(selectedData.get(position),position);
    }

    @Override
    public int getItemCount() {
        return selectedData.size();
    }

    public void addItem(DayClass data) {
        // 외부에서 item을 추가시킬 함수입니다.
        selectedData.add(data);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textview1;
        private TextView textview2;
        private ImageView img;
        private ImageView logo;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            textview1=itemView.findViewById(R.id.schedule_text1);
            textview2=itemView.findViewById(R.id.schedule_text2);
            img=itemView.findViewById(R.id.c_icon);
            logo=itemView.findViewById(R.id.c_logo);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void onBind(DayClass dc, int position) {
            textview1.setText(dc.getUniv_info());
            String detail_info;
            if(dc.getS_or_e()==0){
                detail_info=dc.getHour()+"시 "+dc.getMinute()+"분부터"+dc.getDescription()+" 시작";
                //img.setImageDrawable();
            }else{
                detail_info=dc.getHour()+"시 "+dc.getMinute()+"분까지"+dc.getDescription();
            }
            textview2.setText(detail_info);
            Glide.with(context).load(dc.getLogo()).into(logo);
        }
    }
}