package com.example.administrator.mytimelogger.Adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v13.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.example.administrator.mytimelogger.Fragment.AddActivitiesFragment;
import com.example.administrator.mytimelogger.Fragment.HistoryFragment;
import com.example.administrator.mytimelogger.Fragment.MoreFragment;
import com.example.administrator.mytimelogger.Fragment.AddTagFragment;
import com.example.administrator.mytimelogger.R;


/**
 * Created by Administrator on 2016/8/22.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    int nNumOfTabs;
    public MyFragmentPagerAdapter(FragmentManager fm, int nNumOfTabs, Context context)
    {
        super(fm);
        this.nNumOfTabs=nNumOfTabs;
        this.context = context;
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

    private int[] imageResId = {
            R.drawable.ic_timer_white_24dp,
            R.drawable.ic_import_contacts_white_24dp,
            R.drawable.ic_storage_white_24dp,
            R.drawable.ic_more_horiz_white_24dp
    };
    @Override
    public CharSequence getPageTitle(int position) {
        Drawable image = context.getResources().getDrawable(imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    @Override
    public int getCount() {
        return nNumOfTabs;
    }

}
