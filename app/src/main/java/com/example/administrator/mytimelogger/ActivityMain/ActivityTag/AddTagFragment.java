package com.example.administrator.mytimelogger.ActivityMain.ActivityTag;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.mytimelogger.ActivityMain.ActivityHistory.HistoryListAdapter;
import com.example.administrator.mytimelogger.ActivityMain.BaseFragment;
import com.example.administrator.mytimelogger.R;
import com.example.administrator.mytimelogger.ActivityMain.TagListAdapter;
import com.example.administrator.mytimelogger.db.DB;
import com.example.administrator.mytimelogger.model.Tag;

import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;

/**
 * Created by Administrator on 2016/8/22.
 */
public class AddTagFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private TagListAdapter adapter;
    private List<Tag> mDatas;
    private DB mDB;

    @Override
    public void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_tag);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_tag;
    }

    @Override
    public void loadData() {
        mDB = DB.getInstance(getActivity());
        try {
            mDatas = mDB.loadTagList();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            mDatas = new ArrayList<>();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 设置adapter
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new TagListAdapter(getActivity(), mDatas, false);
        adapter.setOnRecyclerViewItemClickListener(new TagListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Tag tag) {
                Toast.makeText(getActivity(), tag.getName() + " " + tag.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
        getActivity().setTitle("类别");
    }
}
