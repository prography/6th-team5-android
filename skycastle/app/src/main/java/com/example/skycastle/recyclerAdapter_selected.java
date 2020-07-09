package com.example.skycastle;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class recyclerAdapter_selected extends RecyclerView.Adapter<recyclerAdapter_selected.ItemViewHolder>{
    private List<String> selectedData=new ArrayList<String>();
    private Context context;
    private  View view;

    public recyclerAdapter_selected(Context context,List<String> data){
        this.context=context;
        selectedData=data;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public recyclerAdapter_selected.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_recycler_view, parent, false);
        return new ItemViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter_selected.ItemViewHolder holder, int position) {
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
        private TextView del_textview;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            sel_textview=itemView.findViewById(R.id.selected_text);
            del_textview=itemView.findViewById(R.id.delete_button);
            del_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    //SelectUnivPage.selected_list.remove(pos);
                    //Log.d("remove",SelectUnivPage.selected_list.get(pos));
                    selectedData.remove(pos);
                    SelectUnivPage.univ_serverSends.remove(pos);
                    notifyDataSetChanged();
                }
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void onBind(String s, int position) {
            sel_textview.setText(s);
        }
    }
}
