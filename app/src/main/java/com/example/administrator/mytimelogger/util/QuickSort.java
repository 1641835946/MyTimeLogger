package com.example.administrator.mytimelogger.util;

import android.util.Log;

import com.example.administrator.mytimelogger.model.Activities;

import java.util.List;

/**
 * Created by Administrator on 2016/9/4.
 */
public class QuickSort {

    public static void sort(List<Activities> list, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(list, lo, hi);
        sort(list, lo, j-1);
        sort(list, j+1, hi);
    }

    private static int partition(List<Activities> list, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Activities v = list.get(lo);
        while (true) {
//            while (v.compareTo(list.get(++i)) == 1) if(i == hi) break;
//            while (list.get(--j).compareTo(v) == 1) if (j == lo) break;

            //要降序排序
            while (list.get(++i).compareTo(v) == 1) if(i == hi) break;
            while (v.compareTo(list.get(--j)) == 1) if(j == lo) break;
            if (i >= j) break;
            Log.e("partition", "i:" + i);
            Log.e("partition", "j:" + j);
            exch(list, i, j);
        }
        exch(list, lo, j);
        return j;
    }

    private static void exch(List<Activities> list, int i, int j) {
        Activities exchi = list.get(i);
        Activities exchj = list.get(j);
        list.remove(i);
        list.add(i, exchj);
        list.remove(j);
        list.add(j, exchi);
    }

}
