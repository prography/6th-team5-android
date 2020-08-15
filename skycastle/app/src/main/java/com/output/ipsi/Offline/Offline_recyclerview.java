package com.output.ipsi.Offline;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.output.ipsi.R;
import com.output.ipsi.SelectUnivPage;
import com.output.ipsi.ServerData.ServerData;

import java.util.ArrayList;
import java.util.List;

public class Offline_recyclerview extends RecyclerView.Adapter<Offline_recyclerview.ItemViewHolder>{
    private List<ServerData> selectedData=new ArrayList<ServerData>();
    private Context context;
    private View view;
    private List<Integer> offdate_list;

    public Offline_recyclerview(Context context,List<ServerData> selected_data,List<Integer> offdate_list){
        this.context=context;
        selectedData=selected_data;
        this.offdate_list=offdate_list;
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Offline_recyclerview.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offline_recyclerview, parent, false);
        Offline_recyclerview.ItemViewHolder offline_holder= new Offline_recyclerview.ItemViewHolder(view);
        return offline_holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull Offline_recyclerview.ItemViewHolder holder, int position) {
        if(position<offdate_list.size()){
            holder.onBind(offdate_list.get(position),position);
        }

    }

    @Override
    public int getItemCount() {
        return offdate_list.size();
    }

    public void addItem(String data) {
        // 외부에서 item을 추가시킬 함수입니다.
        //selectedData.add(data);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView date_textview;
        private TextView del_textview;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            date_textview=itemView.findViewById(R.id.date_textview);
            //del_textview=itemView.findViewById(R.id.delete_button);
            /*del_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //selectedData.remove(pos);
                    //SelectUnivPage.univ_serverSends.remove(pos);
                    notifyDataSetChanged();
                }
            });*/
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void onBind(int offdate, int position) {
            String date=offdate/100+"월"+offdate%100+"일";
            date_textview.setText(date);
        }
    }
}
