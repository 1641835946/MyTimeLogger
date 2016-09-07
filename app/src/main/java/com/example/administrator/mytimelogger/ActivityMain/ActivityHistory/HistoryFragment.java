package com.example.administrator.mytimelogger.ActivityMain.ActivityHistory;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mytimelogger.ActivityMain.BaseFragment;
import com.example.administrator.mytimelogger.R;
import com.example.administrator.mytimelogger.model.History4View;

import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;

/**
 * Created by Administrator on 2016/8/22.
 */
public class HistoryFragment extends BaseFragment {

    private PinnedSectionListView psListView;

    @Override
    public void initViews(View view) {
        psListView = (PinnedSectionListView) view.findViewById(R.id.pinned_section_list_view);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_history;
    }

    @Override
    public void loadData() {
        HistoryListAdapter adapter = new HistoryListAdapter(getActivity());
        psListView.setAdapter(adapter);
    }
}
