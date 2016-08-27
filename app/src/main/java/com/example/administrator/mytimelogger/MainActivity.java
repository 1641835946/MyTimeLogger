package com.example.administrator.mytimelogger;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.administrator.mytimelogger.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TagListAdapter tagListAdapter;

//    private List<Tag> mDatas = init();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.e("MainActivity: thread id is ", ""+Thread.currentThread().getId());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.activities));
        tabLayout.addTab(tabLayout.newTab().setText(""));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tag));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.more));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        final PagerAdapter adapter = new MyFragmentPagerAdapter
                (getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        // 设置adapter
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        tagListAdapter = new TagListAdapter(mDatas);
//        recyclerView.setAdapter(tagListAdapter);
//    }
//
//    private List<Tag> init() {
//        mDatas = new ArrayList<>();
//        Tag tag1 = new Tag(1, "fight", Color.YELLOW, R.drawable.pause_btn);
//        Tag tag2 = new Tag(2, "hello", Color.GRAY, R.drawable.resume_btn);
//        Tag tag3 = new Tag(3, "world", Color.GREEN, R.drawable.stop_btn);
//        Tag tag4 = new Tag(4, "liang", Color.BLUE, R.drawable.icon);
//        Tag tag5 = new Tag(5, "jie", Color.RED, R.drawable.abc_ic_search_api_mtrl_alpha);
//        mDatas.add(tag1);
//        mDatas.add(tag2);
//        mDatas.add(tag3);
//        mDatas.add(tag4);
//        mDatas.add(tag5);
//        return mDatas;
    }

}
