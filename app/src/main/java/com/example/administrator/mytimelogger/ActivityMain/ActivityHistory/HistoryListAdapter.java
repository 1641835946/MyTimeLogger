package com.example.administrator.mytimelogger.ActivityMain.ActivityHistory;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.mytimelogger.R;
import com.example.administrator.mytimelogger.model.History4View;

import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;

/**
 * Created by Administrator on 2016/8/31.
 */
public class HistoryListAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {

    private final int VIEWTYPE = 1;
    private List<History4View> mDatas;
    private Context context;

    public HistoryListAdapter(Context context, List<History4View> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        History4View item = getItem(position);
        ItemViewHolder itemViewHolder;
        TitleViewHolder titleViewHolder;
        switch (getItemViewType(position)) {
            case 1:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_history_title, null);
                    titleViewHolder = new TitleViewHolder();
                    titleViewHolder.month = (TextView) convertView.findViewById(R.id.month);
                    titleViewHolder.day = (TextView) convertView.findViewById(R.id.day);
                    titleViewHolder.week = (TextView) convertView.findViewById(R.id.week);
                    convertView.setTag(titleViewHolder);
                } else {
                    titleViewHolder = (TitleViewHolder) convertView.getTag();
                }
                titleViewHolder.month.setText("mouth");
                break;
            case 0:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_history, null);
                    itemViewHolder = new ItemViewHolder();
                    itemViewHolder.tagIcon = (AppCompatImageView) convertView.findViewById(R.id.tag_icon);
                    itemViewHolder.tagName = (TextView) convertView.findViewById(R.id.tag_name);
                    itemViewHolder.begin = (TextView) convertView.findViewById(R.id.begin);
                    itemViewHolder.end = (TextView) convertView.findViewById(R.id.end);
                    itemViewHolder.duration = (TextView) convertView.findViewById(R.id.duration);
                    convertView.setTag(itemViewHolder);
                } else {
                    itemViewHolder = (ItemViewHolder) convertView.getTag();
                }
                itemViewHolder.tagName.setText(item.getTag().getName());
                //smallUtil.changeColor 不用set drawable
//        viewHolder.begin.setText(""+item.getActivities().getBeginTime());
                break;
        }

        return convertView;
    }

    @Override
     public boolean isItemViewTypePinned(int viewType) {
        if (viewType == VIEWTYPE) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public History4View getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getCount() {
        if(mDatas == null) {
            mDatas = new ArrayList<>();
        }
        return mDatas.size();
    }

    class ItemViewHolder {
        public AppCompatImageView tagIcon;
        public TextView tagName;
        public TextView duration;
        public TextView begin;
        public TextView end;
    }

    class TitleViewHolder {
        public TextView month;
        public TextView day;
        public TextView week;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getViewType();
    }
}
