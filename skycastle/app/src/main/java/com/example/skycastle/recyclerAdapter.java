package com.example.skycastle;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skycastle.MyDatabase.AppDatabase;
import com.example.skycastle.MyDatabase.BlockData;
import com.example.skycastle.MyDatabase.JhData;
import com.example.skycastle.MyDatabase.univ_img;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.ItemViewHolder>{
    private List<univ_img> listData;
    private Context context;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private ItemViewHolder holder;
    private  View view;
    private int position;
    private AppDatabase db;
    Thread thread;
    List<BlockData> blockData=new ArrayList<BlockData>();

    public recyclerAdapter(Context context, List<univ_img> list,AppDatabase db){
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
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(univ_img data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView u_icon;
        private ConstraintLayout list_con;
        private  univ_img data;
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
                    final String name=listData.get(pos).getUniversity();
                    Log.d("uname",name);

                    Runnable runnable1=new Runnable() {
                        @Override
                        public void run() {
                            blockData= db.infoSaveDao().findBlokcData(name);
                            Log.d("uname2",blockData.get(0).getBlock());
                        }
                    };
                    thread = new Thread(runnable1);
                    thread.start();

                    Runnable runnable2=new Runnable() {
                        @Override
                        public void run() {
                            List<JhData> jhData= db.infoSaveDao().findJhData(name);
                            try{
                                Thread.sleep(200);
                            }catch(InterruptedException e){

                            }
                            Log.d("uname2",blockData.get(0).getBlock());
                            Log.d("uname2",jhData.get(0).getJh());
                            Intent intent=new Intent(context.getApplicationContext(), UnivDetail.class);
                            intent.putExtra("jhData", (Serializable) jhData);
                            intent.putExtra("blockData", (Serializable) blockData);
                            context.startActivity(intent);
                        }
                    };
                    Thread thread2 = new Thread(runnable2);
                    thread2.start();

                }
            });
        }

        public void onBind(univ_img univList, int position) {
            this.data = univList;
            this.position = position;
            final String name = data.getUniversity();
            final String image = data.getSj();

            textView.setText(name);
            //u_icon.set
        }
    }
}
