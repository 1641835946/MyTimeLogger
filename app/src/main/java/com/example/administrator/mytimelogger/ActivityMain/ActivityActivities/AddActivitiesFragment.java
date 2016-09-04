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

import com.example.administrator.mytimelogger.R;
import com.example.administrator.mytimelogger.ActivityMain.TagListAdapter;
import com.example.administrator.mytimelogger.db.DB;
import com.example.administrator.mytimelogger.model.Activities;
import com.example.administrator.mytimelogger.model.CustomSet4View;
import com.example.administrator.mytimelogger.model.Set;
import com.example.administrator.mytimelogger.model.SetItemInOrder;
import com.example.administrator.mytimelogger.model.Tag;
import com.example.administrator.mytimelogger.model.MyTime;
import com.example.administrator.mytimelogger.util.Constant;
import com.example.administrator.mytimelogger.util.SmallUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class AddActivitiesFragment extends Fragment {

    private View view;
    private RecyclerView listRecycler;
    private RecyclerView gridRecycler;
    private TagListAdapter adapter;
    private ActivitiesListAdapter activitiesAdapter;
    private DB mDB;
//    java.lang.NullPointerException
//    private DB mDB=DB.getInstance(getActivity());
    private List<Tag> tagList;
    private List<CustomSet4View> setList;
    private ImageButton pauseBtn;
    private ImageButton stopBtn;
    private TextView durationTv;

    /****************************************生命周期**********************************************/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activities, container, false);
        listRecycler = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        gridRecycler = (RecyclerView) view.findViewById(R.id.grid_recycler_view);
        pauseBtn = (ImageButton) view.findViewById(R.id.pause_img_btn);
        stopBtn = (ImageButton) view.findViewById(R.id.stop_img_btn);
        durationTv = (TextView) view.findViewById(R.id.duration);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDB = DB.getInstance(getActivity());
        initList();
        initGrid();
    }

    @Override
    public void onPause() {
//        super.onPause();
        mDB.updateSetOrder(view2Order(setList));
        for (int i = 0; i < setList.size(); i++) {
            mDB.updateSet(setList.get(i).getSet());
            Log.e("onpause", i + " " + setList.get(i).getSet().getDuration());
        }
        activitiesAdapter.stopThread();
        super.onPause();
    }

    /****************************************private method*******************************************/
    //custom 指grid tag 上的list
    private void addCustom(Tag tag) {
        MyTime time = SmallUtil.gainTime();
        Set set = new Set(tag.getId(), "", 0, time);
        int setId = mDB.saveSet(set);
        set.setSetID(setId);
        CustomSet4View customSet4View = new CustomSet4View(Constant.STATE_PLAY,
                tag,
                set);
        setList.add(0, customSet4View);
        //???????????如果datalist不是从构造函数中传进去而是在adapter中从数据库中读出来的呢？
        activitiesAdapter.notifyChanged();

    }

    private List<SetItemInOrder> view2Order(List<CustomSet4View> customSet4ViewList) {
        List<SetItemInOrder> list = new ArrayList<>();
        for(int i = 0; i < customSet4ViewList.size(); i++) {
            SetItemInOrder setItemInOrder = new SetItemInOrder(
                    customSet4ViewList.get(i).getSet().getSetID(),
                    customSet4ViewList.get(i).getState());
            list.add(setItemInOrder);
        }
        return list;
    }

    private void addActivity(CustomSet4View data) {
        int setId = data.getSet().getSetID();
        MyTime beginTime = data.getSet().getBeginTime();
        MyTime endTime = SmallUtil.gainTime();
        Activities activities = new Activities(setId,
                beginTime,
                endTime,
                SmallUtil.gainIntDuration(beginTime, endTime));
        data.getSet().setDuration(data.getSet().getDuration() + SmallUtil.gainIntDuration(data.getSet().getBeginTime(), endTime));
//        synchronized (this) {//可以直接在data上改吗？？？？？？？？？
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

    private void removeSetList (CustomSet4View data) {
        for (int i = 0; i<setList.size(); i++) {
            if (setList.get(i).getSet().getSetID() == data.getSet().getSetID()) {
                setList.remove(i);
                activitiesAdapter.notifyChanged();
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
        try {
            setList = mDB.loadSetListNotEnded();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            setList = new ArrayList<>();
        }
        activitiesAdapter = new ActivitiesListAdapter(setList);
        activitiesAdapter.setOnItemClickListener(new ActivitiesListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, CustomSet4View data) {
                Toast.makeText(getActivity(), "item is click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStateChangeClick(View view, CustomSet4View data) {
                ImageButton pauseBtn = (ImageButton) view;
                if (data.getState() == Constant.STATE_PLAY) {
                    data.setState(Constant.STATE_PAUSE);
                    pauseBtn.setBackgroundResource(R.drawable.resume_btn);
                    addActivity(data);
                    //当datalist是从构造函数传进去的，如果只是修改呢，需要notify。。。吗？
                    //不需要！！！！！
                } else {
                    data.setState(Constant.STATE_PLAY);
                    data.getSet().setBeginTime(SmallUtil.gainTime());
                    pauseBtn.setBackgroundResource(R.drawable.pause_btn);
                    synchronized (this) {
                        setList.remove(data);
                        setList.add(0, data);
                        activitiesAdapter.notifyChanged();
                    }
                }

            }

            @Override
            public void onEndClick(View view, CustomSet4View data) {
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