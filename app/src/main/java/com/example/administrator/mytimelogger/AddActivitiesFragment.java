package com.example.administrator.mytimelogger;

import android.app.ActionBar;
import android.app.Application;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mytimelogger.db.DB;
import com.example.administrator.mytimelogger.model.Activities;
import com.example.administrator.mytimelogger.model.CustomSet4View;
import com.example.administrator.mytimelogger.model.Set;
import com.example.administrator.mytimelogger.model.Tag;
import com.example.administrator.mytimelogger.model.Time;
import com.example.administrator.mytimelogger.util.Constant;
import com.example.administrator.mytimelogger.util.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class AddActivitiesFragment extends Fragment {

    private View view;
    private LinearLayout outerLayout;
    private RecyclerView listRecycler;
    private RecyclerView gridRecycler;
    private ScrollView scrollView;
    private TagListAdapter adapter;
    private ActivitiesListAdapter activitiesAdapter;

    private List<Tag> mDatas = init();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activities, container, false);
        outerLayout = (LinearLayout) view.findViewById(R.id.outer_layout);
        listRecycler = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        gridRecycler = (RecyclerView) view.findViewById(R.id.grid_recycler_view);
        scrollView = (ScrollView) view.findViewById(R.id.scrollview);
        return view;
    }


//    //fragment的“resume()”中要
//    // 更新：标签列表（除了从数据库中读取数据还有gridView）
//    // 加载：customView（需要从数据库读取Set中未结束的set）
//    private void addCustomView() {
//        //新建一个Set，应在点击GridView的Item时获取Tag的信息（id必须有，name和图标可以有，就可以不用加载数据库了）
//        CustomView customView = new CustomView(MyApplication.getContext(),
//                index + 1,
//                mDatas.get(index).getName(),
//                (int)mDatas.get(index).getIcon());
//        //index++;
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
////        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
////                RelativeLayout.LayoutParams.MATCH_PARENT,
////                RelativeLayout.LayoutParams.WRAP_CONTENT
////        );
//        outerLayout.removeView(gridRecycler);
//        linearLayout.addView(customView, layoutParams);
//
//        outerLayout.addView(gridRecycler);
////        view.invalidate();
//        customView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            //点击查看详情（未结束的activities）
//            }
//        });
//        customView.setControlClick(new CustomView.controlClick() {
//            @Override
//            public void playPauseClick(View view) {
////                String string = view.getParent().getClass().getName();
////                Log.e("AddActivitiesFragment", string);
//                //点击开始或暂停活动（
//                // 开始：新建一个Set（或读取数据：fragment的“onResume”；离开时不需要保存，只需要停止控件更新。
//                // 或重新开始，remove再add，如此才能放到最上面）
//                //      填入开始时间和标签种类（首次必须）以及状态为doing等等。
//                // 暂停：更新Set，将现在时间减去开始时间并填入duration，状态改为暂停并暂停控件更新，
//                //      新建一个Activities（填入信息）
//                CustomView customView = (CustomView) view.getParent();
//                customView.setDoing(!customView.getDoing());
//            }
//
//            @Override
//            public void stopClick(View view) {
//                //停止，removeView，并新建一个Activities
//            }
//
////            @Override
////            public void onItemClick(MotionEvent event, View view) {
////
////            }
//        });
//    }

    private List<Tag> init() {
        mDatas = new ArrayList<>();
        Tag tag1 = new Tag(1, "fight", Color.YELLOW, R.drawable.pause_btn);
        Tag tag2 = new Tag(2, "hello", Color.GRAY, R.drawable.resume_btn);
        Tag tag3 = new Tag(3, "world", Color.GREEN, R.drawable.stop_btn);
        Tag tag4 = new Tag(4, "liang", Color.BLUE, R.drawable.icon);
        Tag tag5 = new Tag(5, "jie", Color.RED, R.drawable.abc_ic_search_api_mtrl_alpha);
        mDatas.add(tag1);mDatas.add(tag2);mDatas.add(tag3);mDatas.add(tag4);mDatas.add(tag5);
        mDatas.add(tag1);mDatas.add(tag2);mDatas.add(tag3);mDatas.add(tag4);mDatas.add(tag5);
        mDatas.add(tag1);mDatas.add(tag2);mDatas.add(tag3);mDatas.add(tag4);mDatas.add(tag5);
        mDatas.add(tag1);mDatas.add(tag2);mDatas.add(tag3);mDatas.add(tag4);mDatas.add(tag5);
        mDatas.add(tag1);mDatas.add(tag2);mDatas.add(tag3);mDatas.add(tag4);mDatas.add(tag5);
        mDatas.add(tag1);mDatas.add(tag2);mDatas.add(tag3);mDatas.add(tag4);mDatas.add(tag5);
        mDatas.add(tag1);mDatas.add(tag2);mDatas.add(tag3);mDatas.add(tag4);mDatas.add(tag5);
        mDatas.add(tag1);mDatas.add(tag2);mDatas.add(tag3);mDatas.add(tag4);mDatas.add(tag5);
        mDatas.add(tag1);mDatas.add(tag2);mDatas.add(tag3);mDatas.add(tag4);mDatas.add(tag5);
        mDatas.add(tag1);mDatas.add(tag2);mDatas.add(tag3);mDatas.add(tag4);mDatas.add(tag5);
        mDatas.add(tag1);mDatas.add(tag2);mDatas.add(tag3);mDatas.add(tag4);mDatas.add(tag5);
        mDatas.add(tag1);mDatas.add(tag2);mDatas.add(tag3);mDatas.add(tag4);mDatas.add(tag5);
        mDatas.add(tag1);mDatas.add(tag2);mDatas.add(tag3);mDatas.add(tag4);mDatas.add(tag5);
        mDatas.add(tag1);mDatas.add(tag2);mDatas.add(tag3);mDatas.add(tag4);mDatas.add(tag5);
        mDatas.add(tag1);mDatas.add(tag2);mDatas.add(tag3);mDatas.add(tag4);mDatas.add(tag5);

        Log.e("Thread", "fragment init " + Thread.currentThread().getId());
        return mDatas;
    }

    private List<CustomSet4View> initDatas() {
        List<CustomSet4View> list = new ArrayList<>();
        Time begin = new Time(2016, 8, 28, 21, 59, 1);
        Set set1 = new Set(1, 1, "nothing", 123456, begin);
        CustomSet4View customSet1 = new CustomSet4View(Constant.STATE_PAUSE, "hello", R.drawable.icon, set1);
        list.add(customSet1);
        return list;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        listRecycler.setHasFixedSize(true);
        listRecycler.setItemAnimator(new DefaultItemAnimator());
        activitiesAdapter = new ActivitiesListAdapter(initDatas());
        activitiesAdapter.setOnItemClickListener(new ActivitiesListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, CustomSet4View data) {
                Toast.makeText(getActivity(), "item is click", Toast.LENGTH_SHORT).show();
                Log.e("liangjie", "item is click");
            }

            @Override
            public void onStateChangeClick(View view, CustomSet4View data) {
                Toast.makeText(getActivity(), "start pause resume", Toast.LENGTH_SHORT).show();
                Log.e("liangjie", "start pause resume");
            }

            @Override
            public void onEndClick(View view, CustomSet4View data) {
                Toast.makeText(getActivity(), "stop", Toast.LENGTH_SHORT).show();
                Log.e("liangjie", "item is stop");
            }
        });
        listRecycler.setAdapter(activitiesAdapter);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 4);
        gridRecycler.setLayoutManager(mLayoutManager);
        gridRecycler.setHasFixedSize(true);
        gridRecycler.setItemAnimator(new DefaultItemAnimator());
        adapter = new TagListAdapter(mDatas, true);
        adapter.setOnRecyclerViewItemClickListener(new TagListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Tag tag) {
                Toast.makeText(getActivity(), tag.getName() + " " + tag.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        gridRecycler.setAdapter(adapter);
        Log.e("Thread", "fragment onActivitycreated " + Thread.currentThread().getId());
    }
}
