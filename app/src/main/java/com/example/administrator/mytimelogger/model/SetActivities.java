package com.example.administrator.mytimelogger.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/23.
 */
public class SetActivities {

    private ActivitiesCustom activitiesCustom;

    private List<ActivitiesInSet> activitiesInSetList;

    class ActivitiesInSet {
        Time beginTime;
        Time endTime;
        long Duration;
    }
}
