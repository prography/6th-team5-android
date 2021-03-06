package com.output.ipsi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;

    private List<UnivDetail_Item> data;
    String sj="";
    String jh="";
    String block="";
    //private Univ_ServerSend senddata;

    public ExpandableListAdapter(List<UnivDetail_Item> data,String name) {
        this.data = data;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);
        switch (type) {
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_header, parent, false);

                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:
                TextView itemTextView = new TextView(context);
                itemTextView.setPadding(subItemPaddingLeft, subItemPaddingTopAndBottom, 0, subItemPaddingTopAndBottom);
                itemTextView.setTextColor(0x88000000);
                itemTextView.setFontFeatureSettings("@font/nanumsquare_r");
                itemTextView.setLayoutParams(
                        new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                return new RecyclerView.ViewHolder(itemTextView) {
                };
        }
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final UnivDetail_Item item = data.get(position);

        switch (item.type) {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.refferalItem = item;
                itemController.header_title.setText(item.text);
                if (item.invisibleChildren == null) {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.ic_arrow_up);
                } else {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.ic_arrow_down);
                }
                itemController.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<UnivDetail_Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.refferalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.ic_arrow_down);
                        } else {
                            int pos = data.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (UnivDetail_Item i : item.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.ic_arrow_up);
                            item.invisibleChildren = null;
                        }
                    }
                });
                break;
            case CHILD:
                TextView itemTextView = (TextView) holder.itemView;
                itemTextView.setText(data.get(position).text);
                holder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if(data.get(position).getInfo_type().equals("sj")) {
                            Log.d("1",data.get(position).text);
                            //UnivDetail.senddata.setSj(data.get(position).text);
                            sj=data.get(position).text;
                            //Log.d("1",UnivDetail.senddata.getSj());
                        }else if(data.get(position).getInfo_type().equals("jh")){
                           // UnivDetail.senddata.setJh(data.get(position).text);
                            jh=data.get(position).text;
                        }else if(data.get(position).getInfo_type().equals("major")){
                            //UnivDetail.senddata.setMajor(data.get(position).text);
                            block=data.get(position).text;
                        }
                        else{
                            Log.d("error","error");
                        }
                        String choice=sj+" "+jh+" "+block;
                        DataEvent dataEvent=new DataEvent(choice);
                        dataEvent.setSj(sj);
                        dataEvent.setJh(jh);
                        dataEvent.setMajor(block);
                        EventBus.getDefault().post(dataEvent);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public ImageView btn_expand_toggle;
        public UnivDetail_Item refferalItem;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.header_title);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
        }
    }

}

