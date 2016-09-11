package com.example.administrator.mytimelogger.Fragment;

import android.util.Log;
import android.view.View;

import com.example.administrator.mytimelogger.R;

/**
 * Created by Administrator on 2016/8/22.
 */
public class MoreFragment extends BaseFragment {

    public static final String TAG = "MoreFragment";

    @Override
    public void initViews(View view) {
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_more;
    }

    @Override
    public void loadData() {
        Log.e("AddActivitiesFragment", "loadData");
//        getActivity().setTitle("More");
    }
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        Log.e(TAG, "onAttach");
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.e(TAG, "onCreate");
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_more, container, false);
//        Log.e(TAG, "onCreateView");
//        return view;
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        Log.e(TAG, "onActivityCreated");
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.e(TAG, "onStart");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.e(TAG, "onResume");
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.e(TAG, "onPause");
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.e(TAG, "onStop");
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Log.e(TAG, "onDestroyView");
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.e(TAG, "onDestroy");
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        Log.e(TAG, "onDetach");
//    }
}
