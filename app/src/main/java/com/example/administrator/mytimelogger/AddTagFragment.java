package com.example.administrator.mytimelogger;

import android.app.Fragment;
import android.graphics.Color;
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
import android.widget.Toast;

import com.example.administrator.mytimelogger.model.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class AddTagFragment extends Fragment {

    private RecyclerView recyclerView;
    private TagListAdapter adapter;

    private List<Tag> mDatas = init();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        Log.e("AddTag:id ", ""+Thread.currentThread().getId());
        Log.e("Thread", "fragment oncreateview " + Thread.currentThread().getId());
        return view;
    }

    private List<Tag> init() {
        mDatas = new ArrayList<>();
        Tag tag1 = new Tag(1, "fight", Color.YELLOW, R.drawable.pause_btn);
        Tag tag2 = new Tag(2, "hello", Color.GRAY, R.drawable.resume_btn);
        Tag tag3 = new Tag(3, "world", Color.GREEN, R.drawable.stop_btn);
        Tag tag4 = new Tag(4, "liang", Color.BLUE, R.drawable.icon);
        Tag tag5 = new Tag(5, "jie", Color.RED, R.drawable.abc_ic_search_api_mtrl_alpha);
        mDatas.add(tag1);
        mDatas.add(tag2);
        mDatas.add(tag3);
        mDatas.add(tag4);
        mDatas.add(tag5);
        Log.e("Thread", "fragment init " + Thread.currentThread().getId());
        return mDatas;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
//        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 设置adapter
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new TagListAdapter(mDatas, false);
        adapter.setOnRecyclerViewItemClickListener(new TagListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Tag tag) {
                Toast.makeText(getActivity(), tag.getName()+" "+tag.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
        Log.e("Thread", "fragment onActivitycreated " + Thread.currentThread().getId());
    }

}
