package com.example.administrator.mytimelogger.Fragment;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.administrator.mytimelogger.Activity.EditAddTagActivity;
import com.example.administrator.mytimelogger.Activity.TagLookingActivity;
import com.example.administrator.mytimelogger.Activity.MainActivity;
import com.example.administrator.mytimelogger.R;
import com.example.administrator.mytimelogger.Adapter.TagListAdapter;
import com.example.administrator.mytimelogger.model.Tag;

/**
 * Created by Administrator on 2016/8/22.
 */
public class AddTagFragment extends BaseFragment {

    private RecyclerView recyclerView;
    public static TagListAdapter adapter;

    @Override
    public void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_tag);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTag = new Intent(getActivity(), EditAddTagActivity.class);
                Tag tag = new Tag("", R.color.colorPrimary, R.drawable.ic_action_timer);
                addTag.putExtra("tag",tag);
                startActivity(addTag);
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_tag;
    }

    @Override
    public void loadData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 设置adapter
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new TagListAdapter(getActivity(), false);
        adapter.setOnRecyclerViewItemClickListener(new TagListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Tag tag) {
                Intent intent = new Intent(getActivity(), TagLookingActivity.class);
                intent.putExtra("tag", tag);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        Log.e("AddActivitiesFragment", "loadData");
//        MainActivity.setAdapter(adapter);
//        getActivity().setTitle("类别");
    }

    @Override
    public void onDestroyView() {
        adapter = null;
        super.onDestroyView();
    }
}
