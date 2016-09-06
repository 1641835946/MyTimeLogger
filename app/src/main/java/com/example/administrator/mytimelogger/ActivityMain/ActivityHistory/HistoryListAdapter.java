package com.example.administrator.mytimelogger.ActivityMain.ActivityHistory;

import android.content.Context;
import android.content.res.Resources;
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
import com.example.administrator.mytimelogger.db.DB;
import com.example.administrator.mytimelogger.model.History4View;
import com.example.administrator.mytimelogger.model.MyTime;
import com.example.administrator.mytimelogger.util.SmallUtil;

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
    private DB mDB;

    public HistoryListAdapter(Context context) {
        this.context = context;
        mDB = DB.getInstance(context);
        try {
            mDatas = mDB.loadDayHistory(SmallUtil.gainTime());
            MyTime begin = mDatas.get(0).getActivities().getBeginTime();
            String beginStr = SmallUtil.yearMouthDay(begin);
            History4View firstTitle = new History4View(VIEWTYPE, beginStr);
            mDatas.add(0,firstTitle);
            for (int i = 1; i < mDatas.size(); i++) {
                String then = SmallUtil.yearMouthDay(mDatas.get(i).getActivities().getBeginTime());
                if (!then.equals(beginStr)) {
                    History4View thenTitle = new History4View(VIEWTYPE, then);
                    mDatas.add(i, thenTitle);
                    i++;
                    beginStr = then;
                }
            }
        } catch(Resources.NotFoundException e){
            mDatas = new ArrayList<>();
        }
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
                    titleViewHolder.day = (TextView) convertView.findViewById(R.id.day);
                    convertView.setTag(titleViewHolder);
                } else {
                    titleViewHolder = (TitleViewHolder) convertView.getTag();
                }
                titleViewHolder.day.setText(item.getYearMouthDay());
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
//                    itemViewHolder.commit = (TextView) convertView.findViewById(R.id.commit);
                    convertView.setTag(itemViewHolder);
                } else {
                    itemViewHolder = (ItemViewHolder) convertView.getTag();
                }
                SmallUtil.changeColor(itemViewHolder.tagIcon, item.getTag());
                itemViewHolder.begin.setText(SmallUtil.timepoint(item.getActivities().getBeginTime()) + "-");
                itemViewHolder.end.setText(SmallUtil.timepoint(item.getActivities().getEndTime()));
//                itemViewHolder.commit.setText();
                itemViewHolder.tagName.setText(item.getTag().getName());
                int durationInt = (int)item.getActivities().getDuration();
                Log.e("duration", "" + durationInt);
                itemViewHolder.duration.setText(SmallUtil.gainHistoryDuration(durationInt));
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
        return mDatas.size();
    }

    class ItemViewHolder {
        public AppCompatImageView tagIcon;
        public TextView tagName;
        public TextView duration;
        public TextView begin;
        public TextView end;
//        public TextView commit;
    }

    class TitleViewHolder {
        public TextView day;
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
