package com.example.administrator.mytimelogger.Activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.administrator.mytimelogger.Adapter.ActivitiesListAdapter;
import com.example.administrator.mytimelogger.Adapter.HistoryListAdapter;
import com.example.administrator.mytimelogger.Adapter.MyFragmentPagerAdapter;
import com.example.administrator.mytimelogger.Adapter.TagListAdapter;
import com.example.administrator.mytimelogger.Fragment.AddActivitiesFragment;
import com.example.administrator.mytimelogger.Fragment.AddTagFragment;
import com.example.administrator.mytimelogger.Fragment.HistoryFragment;
import com.example.administrator.mytimelogger.R;
import com.example.administrator.mytimelogger.db.DB;
import com.example.administrator.mytimelogger.model.ActivityItem4View;
import com.example.administrator.mytimelogger.model.History4View;
import com.example.administrator.mytimelogger.model.MyTime;
import com.example.administrator.mytimelogger.model.SetItemInOrder;
import com.example.administrator.mytimelogger.model.Tag;
import com.example.administrator.mytimelogger.util.SmallUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TagListAdapter tagListAdapter;
    Toolbar toolbar;

//    private List<Tag> mDatas = init();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.e("MainActivity", "onCreate");

        MyFragmentPagerAdapter pagerAdapter;
        ViewPager viewPager;
        TabLayout tabLayout;
        pagerAdapter = new MyFragmentPagerAdapter(getFragmentManager(), 4, this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.activities));
//        tabLayout.addTab(tabLayout.newTab().setText(""));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tag));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.more));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
//        final PagerAdapter adapter = new MyFragmentPagerAdapter
//                (getFragmentManager(), tabLayout.getTabCount(), this);
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//                Log.e("MainActivity", "onTabSelected");
//                Log.e("MainActivity", "tag position " + tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                Log.e("MainActivity", "onTabUnselected");
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                Log.e("MainActivity", "onTabReselected");
//            }
//        });
        mDB = DB.getInstance(this);
    }

//    public void setTitle(String title) {
//        toolbar.setTitle(title);
//    }

    public static DB mDB;
    public static List<Tag> tagList;
    public static List<ActivityItem4View> setList;
    public static List<History4View> historyList;
    private static final int VIEWTYPE = 1;
    public static ActivitiesListAdapter activitiesListAdapter;
    public static HistoryListAdapter historyListAdapter;
    public static TagListAdapter tagListAdapter1;
    public static TagListAdapter tagListAdapter2;
//    public static List<Object> adapterList;
//
//    public static void setAdapter(Object adapter) {
//        if (adapterList == null) {
//            adapterList = new ArrayList<>();
//        }
//        adapterList.add(adapter);
//    }
//
//    public static void updateAdapter(int whichPager) {
////        whichPager :
////                     1 ActivitiesListAdapter
////                     2 HistoryListAdapter
////                     3 TagListAdapter//有两个
//
//        Object adapter;
//        switch (whichPager) {
//            case 1:
//                for(int i = 0; i < adapterList.size(); i++) {
//                    adapter = adapterList.get(i);
//                    if (adapter instanceof ActivitiesListAdapter) {
//                        ((ActivitiesListAdapter) adapter).notifyDataSetChanged();
//                        break;
//                    }
//                }
//                break;
//            case 2:
//                for(int i = 0; i < adapterList.size(); i++) {
//                    adapter = adapterList.get(i);
//                    if (adapter instanceof HistoryListAdapter) {
//                        ((HistoryListAdapter) adapter).notifyDataSetChanged();
//                        Log.e("MainActivity", "update adapter");
//                        break;
//                    }
//                }
//                break;
//            case 3:
//                for(int i = 0; i < adapterList.size(); i++) {
//                    adapter = adapterList.get(i);
//                    if (adapter instanceof TagListAdapter) {
//                        ((TagListAdapter) adapter).notifyDataSetChanged();
//                    }
//                }
//                break;
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
        /*******************AddActivitiesFragment's data*********************/
        /*******************AddTagFragment's data****************************************/
        updateTagList();
        Log.e("MainActivity", "onStart");
        Log.e("MainActivity", "tagList " + tagList.size());
        updateSetList();
        Log.e("MainActivity", "setList " + setList.size());
        /********************HistoryFragment's data**************************************/
        updateActivity();
        Log.e("MainActivity", "historyList " + historyList.size());
        /********************刷新*********************************/
        changedAll();
//        if (adapterList != null && adapterList.size() != 0) {
//            Log.e("MainActivity", "0");
//            Object adapter;
//            for(int i = 0; i < adapterList.size(); i++) {
//                Log.e("MainActivity", "1");
//                if (adapterList.get(i) == null) {
//                    break;
//                }
//                adapter = adapterList.get(i);
//                Log.e("MainActivity", "2");
//                if (adapter instanceof ActivitiesListAdapter) {
//                    Log.e("MainActivity", "3");
//                    ((ActivitiesListAdapter) adapter).notifyDataSetChanged();
//                    Log.e("MainActivity", "update activities");
//                } else if (adapter instanceof HistoryListAdapter) {
//                    ((HistoryListAdapter) adapter).notifyDataSetChanged();
//                    Log.e("MainActivity", "update History");
//                } else if (adapter instanceof TagListAdapter) {
//                    ((TagListAdapter) adapter).notifyDataSetChanged();
//                    Log.e("MainActivity", "update tagList");
//                }
//            }
//        }
    }

    public static void changedActivity() {
        if (HistoryFragment.adapter != null) {
            HistoryFragment.adapter.notifyDataSetChanged();
        }
    }

    public static void changedAll() {
        if (AddActivitiesFragment.adapter != null) {
            AddActivitiesFragment.adapter.notifyDataSetChanged();
        }
        if (AddActivitiesFragment.activitiesAdapter != null) {
            AddActivitiesFragment.activitiesAdapter.notifyDataSetChanged();
        }
        if (AddTagFragment.adapter != null) {
            AddTagFragment.adapter.notifyDataSetChanged();
        }
        changedActivity();
    }

    public static void updateActivity() {
        try {
            historyList = mDB.loadAllHistory();
            MyTime begin = historyList.get(0).getActivities().getBeginTime();
            String beginStr = SmallUtil.yearMouthDay(begin);
            History4View firstTitle = new History4View(VIEWTYPE, beginStr);
            historyList.add(0,firstTitle);
            for (int i = 1; i < historyList.size(); i++) {
                String then = SmallUtil.yearMouthDay(historyList.get(i).getActivities().getBeginTime());
                if (!then.equals(beginStr)) {
                    History4View thenTitle = new History4View(VIEWTYPE, then);
                    historyList.add(i, thenTitle);
                    i++;
                    beginStr = then;
                }
            }
        } catch(Resources.NotFoundException e){
            historyList = new ArrayList<>();
        }
    }

    public static void updateTagList() {
        try {
            tagList = mDB.loadTagList();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            tagList = new ArrayList<>();
        }
    }

    public static void updateSetList() {
        try {
            setList = mDB.loadSetListNotEnded();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            setList = new ArrayList<>();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        //退出前应先更新SetTable，SetOrder
        mDB.updateSetOrder(view2Order(setList));
        for (int i = 0; i < setList.size(); i++) {
            mDB.updateSet(setList.get(i).getSet());
        }
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
}
