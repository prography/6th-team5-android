package com.example.skycastle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeItem item = items.get(position);
        holder.setItem(item);

        holder.setOnItemClickListener(itemClickListener);
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView universityTextView;
        TextView applyTypeTextView;
        TextView dayTextView;

        OnItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            universityTextView = itemView.findViewById(R.id.universityTextView);
            applyTypeTextView = itemView.findViewById(R.id.applyTypeTextView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
        }

        public void setItem(HomeItem item) {
            universityTextView.setText(item.getUniversityName());
            applyTypeTextView.setText(item.getApplyType());
            dayTextView.setText(item.getDay());
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.itemClickListener = listener;
        }
    }
}
