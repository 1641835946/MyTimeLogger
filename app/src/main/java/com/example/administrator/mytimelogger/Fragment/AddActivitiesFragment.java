package com.example.administrator.mytimelogger.Fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mytimelogger.Adapter.ActivitiesListAdapter;
import com.example.administrator.mytimelogger.Activity.MainActivity;
import com.example.administrator.mytimelogger.R;
import com.example.administrator.mytimelogger.Adapter.TagListAdapter;
import com.example.administrator.mytimelogger.model.Activities;
import com.example.administrator.mytimelogger.model.ActivityItem4View;
import com.example.administrator.mytimelogger.model.Set;
import com.example.administrator.mytimelogger.model.Tag;
import com.example.administrator.mytimelogger.model.MyTime;
import com.example.administrator.mytimelogger.util.Constant;
import com.example.administrator.mytimelogger.util.SmallUtil;

/**
 * Created by Administrator on 2016/8/22.
 */
public class AddActivitiesFragment extends BaseFragment {

    private View view;
    private RecyclerView listRecycler;
    private RecyclerView gridRecycler;
    public static TagListAdapter adapter;
    public static ActivitiesListAdapter activitiesAdapter;

//    java.lang.NullPointerException
//    private DB mDB=DB.getInstance(getActivity());
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
        initList();
        initGrid();
        Log.e("AddActivitiesFragment", "loadData");
//        getActivity().setTitle("Activities");
    }

    @Override
    public void onDestroyView() {
        activitiesAdapter.setStopThread();
        activitiesAdapter = null;
        adapter = null;
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
        activitiesAdapter.controlThread(false);
        super.onPause();
    }

    /****************************************private method*******************************************/
    //custom 指grid tag 上的list
    private void addCustom(Tag tag) {
        MyTime time = SmallUtil.gainTime();
        Set set = new Set(tag.getId(), "", 0, time);
        int setId = MainActivity.mDB.saveSet(set);
        set.setSetID(setId);
        ActivityItem4View customSet4View = new ActivityItem4View(Constant.STATE_PLAY,
                tag,
                set);
        MainActivity.setList.add(0, customSet4View);
        activitiesAdapter.notifyChanged();
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
        MainActivity.mDB.saveActivities(activities);
        updateHistoryAdapter();
    }

    private void removeSetList (ActivityItem4View data) {
        for (int i = 0; i<MainActivity.setList.size(); i++) {
            if (MainActivity.setList.get(i).getSet().getSetID() == data.getSet().getSetID()) {
                MainActivity.setList.remove(i);
            }
        }
    }

    /****************************************initial*********************************************/
    private void initGrid() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 4);
        gridRecycler.setLayoutManager(mLayoutManager);
        gridRecycler.setHasFixedSize(true);
        gridRecycler.setItemAnimator(new DefaultItemAnimator());
        adapter = new TagListAdapter(getActivity(), true);
        adapter.setOnRecyclerViewItemClickListener(new TagListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Tag tag) {
                //SetTable +1;
                addCustom(tag);
            }
        });
        gridRecycler.setAdapter(adapter);
//        MainActivity.setAdapter(adapter);
    }

    private void initList() {
        listRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        listRecycler.setHasFixedSize(true);
        listRecycler.setItemAnimator(new DefaultItemAnimator());
        activitiesAdapter = new ActivitiesListAdapter();
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
                    MainActivity.setList.remove(data);
                    MainActivity.setList.add(0, data);
                    activitiesAdapter.notifyChanged();

                }

            }

            @Override
            public void onEndClick(View view, ActivityItem4View data) {
                if (data.getState() == Constant.STATE_PAUSE) {
                    removeSetList(data);
                } else {
                    //state is playing, then table activity +1
                    addActivity(data);
                    removeSetList(data);
                }
                activitiesAdapter.notifyChanged();
            }
        });
        listRecycler.setAdapter(activitiesAdapter);
//        MainActivity.setAdapter(activitiesAdapter);
    }

    private void updateHistoryAdapter() {
        MainActivity.updateActivity();
        MainActivity.changedActivity();
//        MainActivity.updateAdapter(2);
    }
}
