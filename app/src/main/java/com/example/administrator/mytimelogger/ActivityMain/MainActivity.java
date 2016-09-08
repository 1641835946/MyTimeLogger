package com.example.administrator.mytimelogger.ActivityMain;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.administrator.mytimelogger.R;

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
    }

    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

}
