package com.example.skycastle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context context;

    ArrayList<HomeItem> items = new ArrayList<HomeItem>();

    public interface OnItemClickListener {
        void onItemClick(ViewHolder viewHolder, View view, int position);
    }
    private OnItemClickListener itemClickListener;

    public HomeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_home, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final HomeItem item = items.get(position);
        holder.setItem(item);

        holder.setOnItemClickListener(itemClickListener);

        // Check Box Click Listen
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(position).setSelected(holder.checkBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(HomeItem item) {
        items.add(item);
    }

    public void addItems(ArrayList<HomeItem> items) {
        this.items = items;
    }

    public void removeItem() {
        int removeCount = 0;
        for (int i=items.size()-1; i>=0; i--) {
            if (items.get(i).getSelected()) {
                items.remove(i);
                ++removeCount;
            }
        }

        if (removeCount == 0)
            Toast.makeText(context, "삭제할 항목을 선택해 주세요", Toast.LENGTH_LONG).show();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView universityTextView;
        TextView applyTypeTextView;
        TextView dayTextView;
        CheckBox checkBox;

        OnItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            universityTextView = itemView.findViewById(R.id.universityTextView);
            applyTypeTextView = itemView.findViewById(R.id.applyTypeTextView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }

        public void setItem(HomeItem item) {
            universityTextView.setText(item.getUniversityName());
            applyTypeTextView.setText(item.getApplyType());
            dayTextView.setText(item.getDay());
            checkBox.setChecked(false);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.itemClickListener = listener;
        }
    }
}
