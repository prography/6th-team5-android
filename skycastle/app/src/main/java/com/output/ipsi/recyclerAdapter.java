package com.output.ipsi;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.output.ipsi.MyDatabase.AppDatabase;
import com.output.ipsi.MyDatabase.BlockData;
import com.output.ipsi.ServerData_full.ServerData_full;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.ItemViewHolder>{
    private List<ServerData_full> listData;
    private Context context;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private ItemViewHolder holder;
    private  View view;
    private int position;
    private AppDatabase db;
    Thread thread;
    List<BlockData> blockData=new ArrayList<BlockData>();

    public recyclerAdapter(Context context, List<ServerData_full> list, AppDatabase db){
        this.context = (Context) context;
        this.listData = list;
        this.db=db;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.univlist_recycler, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position),position);
        ImageView u_icon = view.findViewById(R.id.u_image);

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(ServerData_full data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView u_icon;
        private LinearLayout list_con;
        private  ServerData_full data;
        private int position;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.u_name);
            u_icon = itemView.findViewById(R.id.u_image);
            list_con = itemView.findViewById(R.id.list_con);
            list_con.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    final String name=listData.get(pos).getName();
                    String logo=listData.get(pos).getLogo();
                    Log.d("uname",name);
                    for(int i=0;i<listData.size();i++){
                        if(listData.get(i).getName().equals(name)){
                            ServerData_full univ_data=listData.get(i);
                            Intent intent=new Intent(context.getApplicationContext(), UnivDetail.class);
                            intent.putExtra("univ_data", (Serializable) univ_data);
                            context.startActivity(intent);
                        }
                    }

                }
            });
        }

        public void onBind(ServerData_full univList, int position) {
            this.data = univList;
            this.position = position;
            final String name = data.getName();
            final String image = data.getLogo();

            textView.setText(name);
            //Glide.with(context).load(image).into(u_icon);
            if(image!=null){
                Glide.with(context).load(image).into(u_icon);
            }else{
                holder.u_icon.setImageDrawable(null);
            }
        }
    }
}
