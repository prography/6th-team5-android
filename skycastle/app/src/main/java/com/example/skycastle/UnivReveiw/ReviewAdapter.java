package com.example.skycastle.UnivReveiw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skycastle.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private Context context;
    private List<ReviewItem> items = null;

    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ViewHolder holder, View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ReviewAdapter(Context context, List<ReviewItem> list) {
        this.context = context;
        this.items = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_review, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewItem item = items.get(position);

        holder.setItem(item);

        holder.setOnItemClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ReviewItem item) {
        items.add(item);
    }

    public void addItems(List<ReviewItem> items) {
        this.items = items;
    }

    public ReviewItem getItem(int position) {
        return items.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView univImageView;
        TextView univTextView;

        OnItemClickListener listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            univImageView = (ImageView) itemView.findViewById(R.id.review_item_univ_icon);
            univTextView = (TextView) itemView.findViewById(R.id.review_item_univ_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(ReviewItem item) {
            univTextView.setText(item.getUnivName());
            univImageView.setImageDrawable(item.getIconDrawble());
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }
    }
}
