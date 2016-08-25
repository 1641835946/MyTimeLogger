package com.example.administrator.mytimelogger.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/23.
 */
public class HistoryList {

    class HistoryAcitivities {
        private ActivitiesCustom activitiesCustom;
        private Time endTime;
    }

    List<HistoryAcitivities> historyAcitivitiesList;
}
