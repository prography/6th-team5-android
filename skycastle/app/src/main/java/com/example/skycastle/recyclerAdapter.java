package com.example.skycastle;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.ItemViewHolder>{
    private List<UnivList> listData;
    private Context context;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private ItemViewHolder holder;
    private  View view;
    private int position;

    public recyclerAdapter(Context context, List<UnivList> list){
        this.context = (Context) context;
        this.listData = list;
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

    void addItem(UnivList data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView u_icon;
        private ConstraintLayout list_con;
        private  UnivList data;
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
                    Intent intent=new Intent(context.getApplicationContext(), UnivDetail.class);
                    intent.putExtra("image", listData.get(pos).getLogo());
                    intent.putExtra("name", listData.get(pos).getName());
                    intent.putExtra("susi_n", listData.get(pos).getSusi_n());
                    intent.putExtra("susi_t", listData.get(pos).getSusi_t());
                    intent.putExtra("major", listData.get(pos).getMajors());
                    context.startActivity(intent);
                }
            });
        }

        public void onBind(UnivList univList, int position) {
            this.data = univList;
            this.position = position;
            final String name = data.getName();
            final String image = data.getLogo();

            textView.setText(name);
            //u_icon.set
        }
    }
}
