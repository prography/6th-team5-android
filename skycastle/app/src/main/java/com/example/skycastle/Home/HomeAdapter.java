package com.example.skycastle.Home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.skycastle.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;
    public Context context;

    List<HomeItem> items;

    public interface OnItemClickListener {
        void onItemClick(ListHeaderViewHolder viewHolder, View view, int position);
    }
    private OnItemClickListener itemClickListener;

    public HomeAdapter(Context context, List<HomeItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        switch (viewType) {
            case HEADER:
                View listView = inflater.inflate(R.layout.item_home_listheader, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(listView);
                return header;
            case CHILD:
                View listChildView = inflater.inflate(R.layout.item_home_listchild, parent, false);
                ListChildViewHolder child = new ListChildViewHolder(listChildView);
                return child;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final HomeItem item = items.get(position);

        switch (item.getShowType()) {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.setItem(item,context);
                itemController.setOnItemClickListener(itemClickListener);

                if (item.getInvisibleChildren() == null) {
                    itemController.dropdownImageView.setImageResource(R.drawable.ic_arrow_up);
                }
                else {
                    itemController.dropdownImageView.setImageResource(R.drawable.ic_arrow_down);
                }
                // Drop down Image Click Listen
                itemController.fullview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item.getInvisibleChildren() == null) {
                            item.setInvisibleChildren(new ArrayList<HomeItem>());
                            int count = 0;
                            int pos = items.indexOf(itemController.refferalItem);
                            while (items.size() > pos+1 && items.get(pos+1).getShowType() == CHILD) {
                                item.getInvisibleChildren().add(items.remove(pos+1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos+1, count);
                            itemController.dropdownImageView.setImageResource(R.drawable.ic_arrow_down);
                        }
                        else {
                            int pos = items.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (HomeItem it : item.getInvisibleChildren()) {
                                items.add(index, it);
                                index++;
                            }
                            notifyItemRangeInserted(pos+1, index-pos-1);
                            itemController.dropdownImageView.setImageResource(R.drawable.ic_arrow_up);
                            item.setInvisibleChildren(null);
                        }
                    }
                });
                break;
            case CHILD:
                final ListChildViewHolder childItemController = (ListChildViewHolder) holder;
                childItemController.setItem(item);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getShowType();
    }

    public void addItem(HomeItem item) {
        items.add(item);
    }

    public void addItems(ArrayList<HomeItem> items) {
        this.items = items;
    }

    public void removeItem() {
        int removeCount = 0;
        HashSet<String> set = new HashSet<>();

        for (HomeItem iter : items) {
            if (iter.getSelected()) {
                set.add(iter.getUniversityName());
            }
        }

        for (int i=items.size()-1; i>=0; i--) {
            if (set.contains(items.get(i).getUniversityName())) {
                items.remove(i);
                removeCount++;
            }
        }

        if (removeCount == 0)
            Toast.makeText(context, "삭제할 항목을 선택해 주세요", Toast.LENGTH_LONG).show();
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView universityTextView;
        TextView applyTypeTextView;
        TextView majorTextView;
        TextView dayTextView;
        ImageView checkBox;
        ImageView dropdownImageView;
        HomeItem refferalItem;
        LinearLayout fullview;

        OnItemClickListener itemClickListener;

        public ListHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            universityTextView = itemView.findViewById(R.id.universityTextView);
            applyTypeTextView = itemView.findViewById(R.id.applyTypeTextView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            checkBox = itemView.findViewById(R.id.checkBox);
            dropdownImageView = itemView.findViewById(R.id.dropdownImageView);
            majorTextView=itemView.findViewById(R.id.major_t);
            fullview=itemView.findViewById(R.id.fullview);
        }

        public void setItem(HomeItem item, Context context) {
            universityTextView.setText(item.getUniversityName());
            applyTypeTextView.setText(item.getApplyType());
            dayTextView.setText(getDday(item.getDay()));
            checkBox.setSelected(false);
            majorTextView.setText(item.getMajor());
            if(item.getUnivId()!=null){
                Glide.with(context).load(item.getUnivId()).into(checkBox);
            }else{
                checkBox.setImageDrawable(null);
            }
            dropdownImageView.setImageResource(R.drawable.ic_arrow_down);

            refferalItem = item;
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.itemClickListener = listener;
        }
    }

    private static String getDday(String targetDay) {
        int tYear, tMonth, tDay;
        int dYear, dMonth, dDay;

        // target day
        String[] targetSlicing = targetDay.split("-");
        dYear = Integer.parseInt(targetSlicing[0]);
        dMonth = Integer.parseInt(targetSlicing[1]);
        dDay = Integer.parseInt(targetSlicing[2]);

        Calendar targetdayCalendar = Calendar.getInstance();
        targetdayCalendar.clear(Calendar.HOUR);
        targetdayCalendar.clear(Calendar.MINUTE);
        targetdayCalendar.clear(Calendar.SECOND);
        targetdayCalendar.clear(Calendar.MILLISECOND);
        targetdayCalendar.set(dYear, dMonth, dDay);

        // today
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.clear(Calendar.HOUR);
        todayCalendar.clear(Calendar.MINUTE);
        todayCalendar.clear(Calendar.SECOND);
        todayCalendar.clear(Calendar.MILLISECOND);

        tYear = todayCalendar.get(Calendar.YEAR);
        tMonth = todayCalendar.get(Calendar.MONTH);
        tDay = todayCalendar.get(Calendar.DAY_OF_MONTH);
        todayCalendar.set(tYear, tMonth+1, tDay);

        long dayDiff = todayCalendar.getTimeInMillis() - targetdayCalendar.getTimeInMillis();

        int resultNumber = (int)(Math.floor(TimeUnit.HOURS.convert(dayDiff, TimeUnit.MILLISECONDS)/24f));
        if (resultNumber > 0) {
            return String.format("D+%d", resultNumber);
        } else if (resultNumber == 0) {
            return "D-day";
        }

        return String.format("D%d", resultNumber);
    }

    private static class ListChildViewHolder extends RecyclerView.ViewHolder {
        TextView list_child_type;
        TextView list_child_day;
        TextView list_end_day;
        HomeItem refferalItem;

        public ListChildViewHolder(View itemView) {
            super(itemView);
            list_child_type = (TextView) itemView.findViewById(R.id.textView_list_child_type);
            list_child_day = (TextView) itemView.findViewById(R.id.textView_list_child_day);
            list_end_day=itemView.findViewById(R.id.textVie_end_day);
        }

        public void setItem(HomeItem item) {
            list_child_type.setText(item.getApplyType());
            String targetDay=item.getDay();
            String[] targetSlicing = targetDay.split("-");
            int dYear = Integer.parseInt(targetSlicing[0]);
            int dMonth = Integer.parseInt(targetSlicing[1]);
            int dDay = Integer.parseInt(targetSlicing[2]);
            String date_t=dMonth+"월 "+dDay+"일";
            list_child_day.setText(date_t);

            targetDay=item.getEnd_day();
            targetSlicing = targetDay.split("-");
            dYear = Integer.parseInt(targetSlicing[0]);
            dMonth = Integer.parseInt(targetSlicing[1]);
            dDay = Integer.parseInt(targetSlicing[2]);
            date_t=dMonth+"월 "+dDay+"일";
            list_end_day.setText(date_t);

            refferalItem = item;
        }
    }
}
