package com.example.administrator.mytimelogger.ActivityMain.ActivityHistory;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mytimelogger.R;
import com.example.administrator.mytimelogger.model.History4View;

import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;

/**
 * Created by Administrator on 2016/8/22.
 */
public class HistoryFragment extends Fragment {

    private PinnedSectionListView psListView;
    private List<History4View> mDatas = init();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        psListView = (PinnedSectionListView) view.findViewById(R.id.pinned_section_list_view);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_history);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HistoryListAdapter adapter = new HistoryListAdapter(getActivity(), mDatas);
        psListView.setAdapter(adapter);
    }

    private List<History4View> init() {
        List<History4View> mDatas = new ArrayList<>();
        History4View one = new History4View(1, "title0");
        History4View two = new History4View(0, "item1");
        History4View three = new History4View(0, "item2");
        History4View four = new History4View(0, "item3");
        History4View five = new History4View(0, "item4");
        History4View six = new History4View(0, "item5");
        History4View seven = new History4View(0, "item6");
        History4View eight = new History4View(0, "item7");
        History4View nine = new History4View(0, "item8");
        History4View ten = new History4View(0, "item9");
        History4View one1 = new History4View(1, "title00");
        History4View two1 = new History4View(0, "item11");
        History4View three1 = new History4View(0, "item22");
        History4View four1 = new History4View(0, "item33");
        History4View five1 = new History4View(0, "item44");
        History4View six1 = new History4View(0, "item55");
        History4View seven1 = new History4View(0, "item66");
        History4View eight1 = new History4View(0, "item77");
        History4View nine1 = new History4View(0, "item88");
        History4View ten1 = new History4View(0, "item99");

        mDatas.add(one);
        mDatas.add(two);
        mDatas.add(three);
        mDatas.add(four);
        mDatas.add(five);
        mDatas.add(six);
        mDatas.add(seven);
        mDatas.add(eight);
        mDatas.add(nine);
        mDatas.add(ten);
        mDatas.add(one1);
        mDatas.add(two1);
        mDatas.add(three1);
        mDatas.add(four1);
        mDatas.add(five1);
        mDatas.add(six1);
        mDatas.add(seven1);
        mDatas.add(eight1);
        mDatas.add(nine1);
        mDatas.add(ten1);
        return mDatas;
    }
}
