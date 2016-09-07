package com.example.administrator.mytimelogger.ActivityMain.ActivityActivities;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mytimelogger.ActivityMain.ActivityHistory.HistoryListAdapter;
import com.example.administrator.mytimelogger.ActivityMain.BaseFragment;
import com.example.administrator.mytimelogger.R;
import com.example.administrator.mytimelogger.ActivityMain.TagListAdapter;
import com.example.administrator.mytimelogger.db.DB;
import com.example.administrator.mytimelogger.model.Activities;
import com.example.administrator.mytimelogger.model.ActivityItem4View;
import com.example.administrator.mytimelogger.model.Set;
import com.example.administrator.mytimelogger.model.SetItemInOrder;
import com.example.administrator.mytimelogger.model.Tag;
import com.example.administrator.mytimelogger.model.MyTime;
import com.example.administrator.mytimelogger.util.Constant;
import com.example.administrator.mytimelogger.util.SmallUtil;

import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;

/**
 * Created by Administrator on 2016/8/22.
 */
public class AddActivitiesFragment extends BaseFragment {

    private View view;
    private RecyclerView listRecycler;
    private RecyclerView gridRecycler;
    private TagListAdapter adapter;
    private ActivitiesListAdapter activitiesAdapter;
    private DB mDB;
//    java.lang.NullPointerException
//    private DB mDB=DB.getInstance(getActivity());
    private List<Tag> tagList;
    private List<ActivityItem4View> setList;
    private ImageButton pauseBtn;
    private ImageButton stopBtn;
    private TextView durationTv;

    /****************************************生命周期**********************************************/

    @Override
    public void initViews(View view) {
        listRecycler = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        gridRecycler = (RecyclerView) view.findViewById(R.id.grid_recycler_view);
        pauseBtn = (ImageButton) view.findViewById(R.id.pause_img_btn);
        stopBtn = (ImageButton) view.findViewById(R.id.stop_img_btn);
        durationTv = (TextView) view.findViewById(R.id.duration);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_activities;
    }

    @Override
    public void loadData() {
        mDB = DB.getInstance(getActivity());
        initList();
        initGrid();
    }

    @Override
    public void onDestroyView() {
        activitiesAdapter.setStopThread();
        super.onDestroyView();
    }


    //onXXX before after
    @Override
    public void onResume() {
        super.onResume();
        if (activitiesAdapter.isAlive()) {
            activitiesAdapter.controlThread(true);
        } else {
            activitiesAdapter.startThread();
        }
//        activitiesAdapter.resumeThread();
    }

    @Override
    public void onPause() {
//        super.onPause();
        //退出前应先更新SetTable，SetOrder
        mDB.updateSetOrder(view2Order(setList));
        for (int i = 0; i < setList.size(); i++) {
            mDB.updateSet(setList.get(i).getSet());
        }
        activitiesAdapter.controlThread(false);
        super.onPause();
    }

    /****************************************private method*******************************************/
    //custom 指grid tag 上的list
    private void addCustom(Tag tag) {
        MyTime time = SmallUtil.gainTime();
        Set set = new Set(tag.getId(), "", 0, time);
        int setId = mDB.saveSet(set);
        set.setSetID(setId);
        ActivityItem4View customSet4View = new ActivityItem4View(Constant.STATE_PLAY,
                tag,
                set);
        setList.add(0, customSet4View);
        activitiesAdapter.notifyChanged();
    }

    private List<SetItemInOrder> view2Order(List<ActivityItem4View> customSet4ViewList) {
        List<SetItemInOrder> list = new ArrayList<>();
        for(int i = 0; i < customSet4ViewList.size(); i++) {
            SetItemInOrder setItemInOrder = new SetItemInOrder(
                    customSet4ViewList.get(i).getSet().getSetID(),
                    customSet4ViewList.get(i).getState());
            list.add(setItemInOrder);
        }
        return list;
    }

    private void addActivity(ActivityItem4View data) {
        int setId = data.getSet().getSetID();
        MyTime beginTime = data.getSet().getBeginTime();
        MyTime endTime = SmallUtil.gainTime();
        Activities activities = new Activities(setId,
                beginTime,
                endTime,
                SmallUtil.gainIntDuration(beginTime, endTime));
        data.getSet().setDuration(data.getSet().getDuration() + SmallUtil.gainIntDuration(data.getSet().getBeginTime(), endTime));
//        synchronized (this) {//可以直接在data上改吗？可以，因为这的数据与adapter是同一引用
//            for (int i = 0; i<setList.size(); i++) {
//                if (setList.get(i).getSet().getSetID() == setId) {
//                    Set set = setList.get(i).getSet();
//                    //更新duration（duration为所有的Activities的总时长）
//                    set.setDuration(set.getDuration() + SmallUtil.gainIntDuration(set.getBeginTime(), endTime));
//                }
//            }
//        }
        mDB.saveActivities(activities);
    }

    private void removeSetList (ActivityItem4View data) {
        for (int i = 0; i<activitiesAdapter.getItemCount(); i++) {
            if (setList.get(i).getSet().getSetID() == data.getSet().getSetID()) {
                setList.remove(i);
            }
        }
    }

    /****************************************initial*********************************************/
    private void initGrid() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 4);
        gridRecycler.setLayoutManager(mLayoutManager);
        gridRecycler.setHasFixedSize(true);
        gridRecycler.setItemAnimator(new DefaultItemAnimator());
        try {
            tagList = mDB.loadTagList();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            tagList = new ArrayList<>();
        }
        adapter = new TagListAdapter(getActivity(), tagList, true);
        adapter.setOnRecyclerViewItemClickListener(new TagListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Tag tag) {
                //SetTable +1;
                addCustom(tag);
            }
        });
        gridRecycler.setAdapter(adapter);
    }

    private void initList() {
        listRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        listRecycler.setHasFixedSize(true);
        listRecycler.setItemAnimator(new DefaultItemAnimator());
        DB mDB = DB.getInstance(getActivity());
        try {
            setList = mDB.loadSetListNotEnded();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            setList = new ArrayList<>();
        }
        activitiesAdapter = new ActivitiesListAdapter(setList);
        activitiesAdapter.setOnItemClickListener(new ActivitiesListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, ActivityItem4View data) {
                Toast.makeText(getActivity(), "item is click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStateChangeClick(View view, ActivityItem4View data) {
                ImageButton pauseBtn = (ImageButton) view;
                if (data.getState() == Constant.STATE_PLAY) {
                    data.setState(Constant.STATE_PAUSE);
                    pauseBtn.setBackgroundResource(R.drawable.resume_btn);
                    addActivity(data);
                } else {
                    data.setState(Constant.STATE_PLAY);
                    data.getSet().setBeginTime(SmallUtil.gainTime());
                    pauseBtn.setBackgroundResource(R.drawable.pause_btn);
                    setList.remove(data);
                    setList.add(0, data);
                    activitiesAdapter.notifyChanged();
                }

            }

            @Override
            public void onEndClick(View view, ActivityItem4View data) {
                if (data.getState() == Constant.STATE_PAUSE) {}
                else {
                    //state is playing, then table activity +1
                    addActivity(data);
                }
                removeSetList(data);
                activitiesAdapter.notifyChanged();
            }
        });
        listRecycler.setAdapter(activitiesAdapter);
    }
}
