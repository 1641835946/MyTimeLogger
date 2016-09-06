package com.example.administrator.mytimelogger.util;

import com.example.administrator.mytimelogger.model.Activities;
import com.example.administrator.mytimelogger.model.History4View;

import java.util.List;

/**
 * Created by Administrator on 2016/9/5.
 */
public class InsertionSort {

    public static void sort(List<Activities> list) {
        int N = list.size();
        for (int i = 1; i < N; i++) {
            //降序排列
            for (int j = i; j > 0 && more(list.get(i), list.get(j)); j--) {
                exch(list, j, j-1);
            }
        }
    }

    private static boolean more(Activities a, Activities b) {
        int i = a.compareTo(b);
        if (i == 1) {
            return true;
        } else {
            return false;
        }
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
