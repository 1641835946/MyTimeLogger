package com.example.administrator.mytimelogger;

import android.app.Application;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.mytimelogger.util.MyApplication;

/**
 * Created by Administrator on 2016/8/22.
 */
public class AddActivitiesFragment extends Fragment {

    CustomView customView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activities, container, false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear_layout);
        customView = new CustomView(MyApplication.getContext(), 1, "fighting", R.drawable.icon);
        linearLayout.addView(customView);
        customView.setControlClick(new CustomView.controlClick() {
            @Override
            public void playPauseClick() {
                customView.setDoing(!customView.getDoing());
            }

            @Override
            public void stopClick() {

            }

            @Override
            public void onItemClick() {

            }
        });
        return view;
    }

}
