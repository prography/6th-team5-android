package com.output.ipsi.Offline;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.output.ipsi.R;
import com.output.ipsi.SelectUnivPage;
import com.output.ipsi.recyclerAdapter_selected;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class Off_select_recyclerview extends RecyclerView.Adapter<Off_select_recyclerview.ItemViewHolder>{
    private List<String> selectedData=new ArrayList<String>();
    private Context context;
    private View view;
    private String type;

    public Off_select_recyclerview(Context context,List<String> data, String type){
        this.context=context;
        selectedData=data;
        this.type=type;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Off_select_recyclerview.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offline_choiceview, parent, false);
        return new Off_select_recyclerview.ItemViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull Off_select_recyclerview.ItemViewHolder holder, int position) {
        holder.onBind(selectedData.get(position),position);

    }

    @Override
    public int getItemCount() {
        return selectedData.size();
    }

    public void addItem(String data) {
        // 외부에서 item을 추가시킬 함수입니다.
        selectedData.add(data);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView sel_textview;
        private LinearLayout sel_view;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            sel_textview=itemView.findViewById(R.id.offline_choice_text);
            sel_view=itemView.findViewById(R.id.off_choice_view);
            sel_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    DataEvent_offline dataEvent_offline=new DataEvent_offline(selectedData.get(pos));
                    dataEvent_offline.setType(type);
                    EventBus.getDefault().post(dataEvent_offline);
                }
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void onBind(String s, int position) {
            sel_textview.setText(s);
        }
    }
}