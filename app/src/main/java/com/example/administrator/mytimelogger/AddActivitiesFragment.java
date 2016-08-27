package com.example.administrator.mytimelogger;

import android.app.Application;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.mytimelogger.db.DB;
import com.example.administrator.mytimelogger.model.Activities;
import com.example.administrator.mytimelogger.model.Tag;
import com.example.administrator.mytimelogger.util.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class AddActivitiesFragment extends Fragment {

    private List<CustomView> customViewList = new ArrayList<>();
    private int index = 0;
    private View view;
    private LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activities, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.linear_layout);
        Log.e("AddFragment:id ", ""+Thread.currentThread().getId());
        return view;
    }


    //fragment的“resume()”中要
    // 更新：标签列表（除了从数据库中读取数据还有gridView）
    // 加载：customView（需要从数据库读取Set中未结束的set）

//    private void addCustomView() {
//        //新建一个Set，应在点击GridView的Item时获取Tag的信息（id必须有，name和图标可以有，就可以不用加载数据库了）
//        CustomView customView = new CustomView(MyApplication.getContext(), ++index, tag);
//        linearLayout.addView(customView);
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
//                CustomView customView = (CustomView)view.getParent();
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

}
