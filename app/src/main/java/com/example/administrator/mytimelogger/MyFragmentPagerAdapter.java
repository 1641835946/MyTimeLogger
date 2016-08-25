package com.example.administrator.mytimelogger;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;


/**
 * Created by Administrator on 2016/8/22.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    int nNumOfTabs;
    public MyFragmentPagerAdapter(FragmentManager fm, int nNumOfTabs)
    {
        super(fm);
        this.nNumOfTabs=nNumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                Fragment tab1 = new AddActivitiesFragment();
                return tab1;
            case 1:
                Fragment tab2 = new HistoryFragment();
                return tab2;
            case 2:
                Fragment tab3 = new AddTagFragment();
                return tab3;
            case 3:
                Fragment tab4 = new MoreFragment();
                return tab4;
        }
        return null;
    }

    @Override
    public int getCount() {
        return nNumOfTabs;
    }
}
