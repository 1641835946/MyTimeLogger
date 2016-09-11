package com.example.administrator.mytimelogger.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.mytimelogger.model.Activities;
import com.example.administrator.mytimelogger.model.ActivityItem4View;
import com.example.administrator.mytimelogger.model.History4View;
import com.example.administrator.mytimelogger.model.Set;
import com.example.administrator.mytimelogger.model.SetItemInOrder;
import com.example.administrator.mytimelogger.model.Tag;
import com.example.administrator.mytimelogger.model.MyTime;
import com.example.administrator.mytimelogger.util.Constant;
import com.example.administrator.mytimelogger.util.QuickSort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class DB {

    public static final String TAG = "DB";
    public static final String DB_NAME = "MyTimeLoggerDB";

    public static final int VERSION = 1;

    private static DB mDB;

    private SQLiteDatabase db;

    private DB(Context context) {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, VERSION);
        db = dbOpenHelper.getWritableDatabase();
    }

    public synchronized static DB getInstance(Context context) {
        if (mDB == null) {
            mDB = new DB(context);
        }
        return mDB;
    }

    //保存标签具体信息
    public int saveTag(Tag tag) {
        int id = 0;
        if (tag != null) {
            ContentValues values = new ContentValues();
            values.put(Constant.TAG_NAME, tag.getName());
            values.put(Constant.TAG_COLOR, tag.getColor());
            values.put(Constant.TAG_ICON, tag.getIcon());
            db.insert(Constant.TABLE_TAG, null, values);
            Cursor cursor = db.query(Constant.TABLE_TAG, null,
                    Constant.TAG_NAME + " = ? and " + Constant.TAG_COLOR + " = ? and " + Constant.TAG_ICON + " = ?",
                    new String[] {tag.getName(), String.valueOf(tag.getColor()), String.valueOf(tag.getIcon())},
                    null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndex("id"));
                } while (cursor.moveToNext());
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return id;
    }

    //？my_problem:需要check一下set的成员变量是否为null吗？
    //保存群组（含一个或多个活动）
    public int saveSet(Set set) {
        int id = 0;
        if (set != null) {
            ContentValues values = new ContentValues();
            //autoincrement id是自增长的，需要读取但不需要保存。
            values.put(Constant.SET_TAG_ID, set.getTagID());
            values.put(Constant.SET_COMMIT, set.getCommit());
            values.put(Constant.SET_DURATION, set.getDuration());
            values.put(Constant.SET_BEGIN_YEAR, set.getBeginTime().getYear());
            values.put(Constant.SET_BEGIN_MONTH, set.getBeginTime().getMonth());
            values.put(Constant.SET_BEGIN_DAY, set.getBeginTime().getDay());
            values.put(Constant.SET_BEGIN_HOUR, set.getBeginTime().getHour());
            values.put(Constant.SET_BEGIN_MINUTE, set.getBeginTime().getMinute());
            values.put(Constant.SET_BEGIN_SECOND, set.getBeginTime().getSecond());
            db.insert(Constant.TABLE_SET, null, values);
            Cursor cursor = db.query(Constant.TABLE_SET, null,
                    Constant.SET_BEGIN_YEAR + " = ? and " +
                            Constant.SET_BEGIN_MONTH + " = ? and " +
                            Constant.SET_BEGIN_DAY + " = ? and " +
                            Constant.SET_BEGIN_HOUR + " = ? and " +
                            Constant.SET_BEGIN_MINUTE + " = ? and " +
                            Constant.SET_BEGIN_SECOND + " = ?",
                    new String[] {""+set.getBeginTime().getYear(),
                            ""+set.getBeginTime().getMonth(),
                            ""+set.getBeginTime().getDay(),
                            ""+set.getBeginTime().getHour(),
                            ""+set.getBeginTime().getMinute(),
                            ""+set.getBeginTime().getSecond()},
                    null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndex("id"));
                } while (cursor.moveToNext());
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return id;
    }

    public void updateSet(Set set) {
        if (set != null) {
            ContentValues values = new ContentValues();
            //autoincrement id是自增长的，需要读取但不需要保存。
            values.put(Constant.SET_COMMIT, set.getCommit());
            values.put(Constant.SET_DURATION, set.getDuration());
            values.put(Constant.SET_BEGIN_YEAR, set.getBeginTime().getYear());
            values.put(Constant.SET_BEGIN_MONTH, set.getBeginTime().getMonth());
            values.put(Constant.SET_BEGIN_DAY, set.getBeginTime().getDay());
            values.put(Constant.SET_BEGIN_HOUR, set.getBeginTime().getHour());
            values.put(Constant.SET_BEGIN_MINUTE, set.getBeginTime().getMinute());
            values.put(Constant.SET_BEGIN_SECOND, set.getBeginTime().getSecond());
            db.update(Constant.TABLE_SET, values, "id = ?", new String[] {"" + set.getSetID()});
        }
    }
    //保存活动（Activities区别于android中的Activity）
    public void saveActivities(Activities activities) {
        if (activities != null) {
            ContentValues values = new ContentValues();
            values.put(Constant.ACTIVITY_SET_ID, activities.getSetId());
            values.put(Constant.ACTIVITY_DURATION, activities.getDuration());
            values.put(Constant.ACTIVITY_BEGIN_YEAR, activities.getBeginTime().getYear());
            values.put(Constant.ACTIVITY_BEGIN_MONTH, activities.getBeginTime().getMonth());
            values.put(Constant.ACTIVITY_BEGIN_DAY, activities.getBeginTime().getDay());
            values.put(Constant.ACTIVITY_BEGIN_HOUR, activities.getBeginTime().getHour());
            values.put(Constant.ACTIVITY_BEGIN_MINUTE, activities.getBeginTime().getMinute());
            values.put(Constant.ACTIVITY_BEGIN_SECOND, activities.getBeginTime().getSecond());
            values.put(Constant.ACTIVITY_END_YEAR, activities.getEndTime().getYear());
            values.put(Constant.ACTIVITY_END_MONTH, activities.getEndTime().getMonth());
            values.put(Constant.ACTIVITY_END_DAY, activities.getEndTime().getDay());
            values.put(Constant.ACTIVITY_END_HOUR, activities.getEndTime().getHour());
            values.put(Constant.ACTIVITY_END_MINUTE, activities.getEndTime().getMinute());
            values.put(Constant.ACTIVITY_END_SECOND, activities.getEndTime().getSecond());
            db.insert(Constant.TABLE_ACTIVITY, null, values);
        }
    }

//    //添加标签//直接更新标签顺序，id才不会错
//    public void addTagOrder(int tagId) {
//        ContentValues values = new ContentValues();
//        values.put(Constant.ORDER_TAG_ID, tagId);
//        db.insert(Constant.TABLE_ORDER, null, values);
//    }

    //更新标签顺序
    public void updateTagOrder(List<Integer> tagIdList) {
        if (tagIdList != null) {
            db.delete(Constant.TABLE_TAG_ORDER, null, null);
            if (tagIdList.size() == 0) {
                return;
            }
            ContentValues values = new ContentValues();
            for (int i = 0; i < tagIdList.size(); i++) {
                values.put("id", i+1+"");
                values.put(Constant.ORDER_TAG_ID, tagIdList.get(i));
                db.insert(Constant.TABLE_TAG_ORDER, null, values);
                values.clear();
            }
        }
    }

    //加载标签列表（有顺序）
    public List<Integer> loadTagOrder() throws Resources.NotFoundException {
        boolean exist = false;
        Cursor cursor = db.query(Constant.TABLE_TAG_ORDER, null, null, null, null, null, null);
        List<Integer> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            exist = true;
            int tagId;
            do {
                tagId = cursor.getInt(cursor.getColumnIndex(Constant.ORDER_TAG_ID));
                list.add(tagId);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        if (exist) {return list;}
        else {throw new Resources.NotFoundException();}
    }

    //更新set顺序
    public void updateSetOrder(List<SetItemInOrder> setList) {
        if (setList != null) {
            db.delete(Constant.TABLE_SET_ORDER, null, null);
            if (setList.size() == 0) {
                return;
            }
            ContentValues values = new ContentValues();
            for (int i = 0; i < setList.size(); i++) {
                values.put("id", i + 1 + "");
                Log.e("set id is", " " + setList.get(i).getSetID());
                Log.e("set state is " , "" + setList.get(i).getState());
                values.put(Constant.ORDER_SET_ID, setList.get(i).getSetID());
                values.put(Constant.ORDER_SET_STATE, setList.get(i).getState());
                db.insert(Constant.TABLE_SET_ORDER, null, values);
                values.clear();
            }
        }
    }

    //加载set列表（有顺序）
    public List<SetItemInOrder> loadSetOrder() throws Resources.NotFoundException {
        boolean exist = false;
        Cursor cursor = db.query(Constant.TABLE_SET_ORDER, null, null, null, null, null, null);
        List<SetItemInOrder> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            exist = true;
            do {
                SetItemInOrder setItemInOrder = new SetItemInOrder();
                setItemInOrder.setSetID(cursor.getInt(cursor.getColumnIndex(Constant.ORDER_SET_ID)));
                setItemInOrder.setState(cursor.getInt(cursor.getColumnIndex(Constant.ORDER_SET_STATE)));
                list.add(setItemInOrder);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        if (exist) {return list;}
        else {throw new Resources.NotFoundException();}
    }

    //有顺序的标签list
    public List<Tag> loadTagList() throws Resources.NotFoundException {
        List<Integer> tagList = loadTagOrder();
        List<Tag> list = new ArrayList<>();
        for (int i = 0; i < tagList.size(); i++) {
            Tag tag =  loadTag(tagList.get(i));
            list.add(tag);
        }
        return list;
    }

    static final String TAG1 = "loadSetListNotEnded";
    //加载未完成的一列群组（群组是单数）的具体信息
    public List<ActivityItem4View> loadSetListNotEnded() throws Resources.NotFoundException {
        boolean exist = false;
        List<ActivityItem4View> list = new ArrayList<>();
        try {
            List<SetItemInOrder> setItemInOrderList = loadSetOrder();
            exist = true;
            for (int i = 0; i<setItemInOrderList.size(); i++) {
                ActivityItem4View customSet4View = new ActivityItem4View();
                int setID = setItemInOrderList.get(i).getSetID();
                Set set = loadSet(setID);
                Tag tag = loadTag(set.getTagID());
                customSet4View.setSet(set);
                customSet4View.setState(setItemInOrderList.get(i).getState());
                customSet4View.setTag(tag);
                list.add(customSet4View);
                Log.e(TAG1, "setid " + setID);
                Log.e(TAG1, "begin time" + set.getBeginTime().toString());
                Log.e(TAG1, "duration" + set.getDuration());
            }
        } catch (Resources.NotFoundException e) {
        }
        if (exist) {return list;}
        else {throw new Resources.NotFoundException();}
    }
    //加载标签的具体信息
    public Tag loadTag(int tagId) throws Resources.NotFoundException {
        boolean exist = false;
        Cursor cursor = db.query(Constant.TABLE_TAG, null, "id = ?",
                new String[] {String.valueOf(tagId)}, null, null, null);
        Tag tag = null;
        if (cursor.moveToFirst()) {
            exist = true;
            do {
                tag = new Tag();
                tag.setId(tagId);
                tag.setName(cursor.getString(cursor.getColumnIndex(Constant.TAG_NAME)));
                tag.setColor(cursor.getInt(cursor.getColumnIndex(Constant.TAG_COLOR)));
                tag.setIcon(cursor.getInt(cursor.getColumnIndex(Constant.TAG_ICON)));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        if (exist) {return tag;}
        else {throw new Resources.NotFoundException();}
    }

    //load set item that not ended
    public Set loadSet(int setId) throws Resources.NotFoundException {
        boolean exist = false;
        Cursor cursor = db.query(Constant.TABLE_SET, null, "id = ?",
                new String[]{String.valueOf(setId)}, null, null, null);
        Set set = null;
        if (cursor.moveToFirst()) {
            do {
                exist = true;
                set = new Set();
                set.setSetID(setId);
                set.setTagID(cursor.getInt(cursor.getColumnIndex(Constant.SET_TAG_ID)));
                MyTime beginTime = new MyTime();
                beginTime.setYear(cursor.getInt(cursor.getColumnIndex(Constant.SET_BEGIN_YEAR)));
                beginTime.setMonth(cursor.getInt(cursor.getColumnIndex(Constant.SET_BEGIN_MONTH)));
                beginTime.setDay(cursor.getInt(cursor.getColumnIndex(Constant.SET_BEGIN_DAY)));
                beginTime.setHour(cursor.getInt(cursor.getColumnIndex(Constant.SET_BEGIN_HOUR)));
                beginTime.setMinute(cursor.getInt(cursor.getColumnIndex(Constant.SET_BEGIN_MINUTE)));
                beginTime.setSecond(cursor.getInt(cursor.getColumnIndex(Constant.SET_BEGIN_SECOND)));
                set.setBeginTime(beginTime);
                set.setCommit(cursor.getString(cursor.getColumnIndex(Constant.SET_COMMIT)));
                set.setDuration(cursor.getInt(cursor.getColumnIndex(Constant.SET_DURATION)));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        if (exist) {return set;}
        else {throw new Resources.NotFoundException();}
    }

    /************************load activities list******************************/
//    private Cursor loadTodayCursor(MyTime today) throws Resources.NotFoundException{
//        Cursor cursor = db.query(Constant.TABLE_ACTIVITY, null,
//                Constant.ACTIVITY_BEGIN_YEAR + " = ? and " +
//                        Constant.ACTIVITY_BEGIN_MONTH + " = ? and " +
//                        Constant.ACTIVITY_BEGIN_DAY + " = ?",
//                new String[]{String.valueOf(today.getYear()),
//                        String.valueOf(today.getMonth()),
//                        String.valueOf(today.getDay())}, null, null, null);
//        return cursor;
//    }

//    private Cursor loadYesterdayCursor(MyTime yesterday) throws Resources.NotFoundException{
//        Cursor cursor = db.query(Constant.TABLE_ACTIVITY, null,
//                Constant.ACTIVITY_END_YEAR + " = ? and " +
//                        Constant.ACTIVITY_END_MONTH + " = ? and " +
//                        Constant.ACTIVITY_END_DAY + " = ?",
//                new String[]{String.valueOf(yesterday.getYear()),
//                        String.valueOf(yesterday.getMonth()),
//                        String.valueOf(yesterday.getDay())}, null, null, null);
//        return cursor;
//    }

//    private void loadDayActivities(int whatDay, MyTime day, List<Activities> list, List<Integer> setIdList) throws Resources.NotFoundException{
//        Cursor cursor;
//        boolean exist = false;
////        switch (whatDay) {
////            case -1:
////                cursor = loadYesterdayCursor(day);
////                break;
////            default:
//                cursor = loadTodayCursor(day);
////                break;
////        }
//        Activities activities;
//        if (cursor.moveToFirst()) {
//            do {
//                exist = true;
//                activities = new Activities();
//                int activitiesId = cursor.getInt(cursor.getColumnIndex("id"));
//                if (setIdList.contains(activitiesId)) {
//                    continue;
//                }
//                setIdList.add(activitiesId);
//                activities.setSetId(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_SET_ID)));
//                activities.setDuration(cursor.getLong(cursor.getColumnIndex(Constant.ACTIVITY_DURATION)));
//                MyTime beginTime = new MyTime();
//                beginTime.setYear(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_BEGIN_YEAR)));
//                beginTime.setMonth(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_BEGIN_MONTH)));
//                beginTime.setDay(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_BEGIN_DAY)));
//                beginTime.setHour(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_BEGIN_HOUR)));
//                beginTime.setMinute(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_BEGIN_MINUTE)));
//                beginTime.setSecond(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_BEGIN_SECOND)));
//                activities.setBeginTime(beginTime);
//                MyTime endTime = new MyTime();
//                endTime.setYear(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_END_YEAR)));
//                endTime.setMonth(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_END_MONTH)));
//                endTime.setDay(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_END_DAY)));
//                endTime.setHour(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_END_HOUR)));
//                endTime.setMinute(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_END_MINUTE)));
//                endTime.setSecond(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_END_SECOND)));
//                activities.setEndTime(endTime);
//                list.add(activities);
//            } while (cursor.moveToNext());
//        }
//        if (cursor != null) {
//            cursor.close();
//        }
//        if (!exist) {
//            throw new Resources.NotFoundException();
//        }
//    }
//排序并加载对应的Tag
//    public List<History4View> loadDayHistory(MyTime today) throws Resources.NotFoundException{
//        int exist = 2;
//        MyTime yesterday = new MyTime(today.getYear(), today.getMonth(), today.getDay()-1);
//        List<Activities> list = new LinkedList<>();
//        List<Integer> setIdList = new ArrayList<>();
//        List<History4View> backList = new ArrayList<>();
//        try {
//            loadDayActivities(-1, yesterday, list, setIdList);
//        } catch (Resources.NotFoundException e) {
//            exist -= 1;
//        }
//        try {
//            loadDayActivities(0, today, list, setIdList);
//        } catch (Resources.NotFoundException e) {
//            exist -= 1;
//        }
//        if (exist == 0) {
//            throw new Resources.NotFoundException();
//        }
//        QuickSort.sort(list, 0, list.size() - 1);
//        for (int i = 0; i < list.size(); i++) {
//            Set set = loadSet(list.get(i).getSetId());
//            Tag tag = loadTag(set.getTagID());
//            History4View history4View = new History4View(list.get(i), tag, 0);
//            backList.add(history4View);
//        }
//        return backList;
//    }

    private Cursor loadAllCursor() throws Resources.NotFoundException {
        Cursor cursor = db.query(Constant.TABLE_ACTIVITY, null, null, null, null,
                null, null);
        return cursor;
    }
    private void loadAllActivities(List<Activities> list) throws Resources.NotFoundException{
        Cursor cursor;
        boolean exist = false;
        cursor = loadAllCursor();
        Activities activities;
        if (cursor.moveToFirst()) {
            do {
                exist = true;
                activities = new Activities();
                activities.setSetId(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_SET_ID)));
                activities.setDuration(cursor.getLong(cursor.getColumnIndex(Constant.ACTIVITY_DURATION)));
                MyTime beginTime = new MyTime();
                beginTime.setYear(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_BEGIN_YEAR)));
                beginTime.setMonth(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_BEGIN_MONTH)));
                beginTime.setDay(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_BEGIN_DAY)));
                beginTime.setHour(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_BEGIN_HOUR)));
                beginTime.setMinute(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_BEGIN_MINUTE)));
                beginTime.setSecond(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_BEGIN_SECOND)));
                activities.setBeginTime(beginTime);
                MyTime endTime = new MyTime();
                endTime.setYear(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_END_YEAR)));
                endTime.setMonth(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_END_MONTH)));
                endTime.setDay(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_END_DAY)));
                endTime.setHour(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_END_HOUR)));
                endTime.setMinute(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_END_MINUTE)));
                endTime.setSecond(cursor.getInt(cursor.getColumnIndex(Constant.ACTIVITY_END_SECOND)));
                activities.setEndTime(endTime);
                list.add(activities);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        if (!exist) {
            throw new Resources.NotFoundException();
        }
    }

    public List<History4View> loadAllHistory() throws Resources.NotFoundException {
        List<Activities> list = new LinkedList<>();
        List<History4View> backList = new ArrayList<>();
        loadAllActivities(list);
        if (list.size() == 0) {
            throw new Resources.NotFoundException();
        }
        QuickSort.sort(list, 0, list.size() - 1);
        for (int i = 0; i < list.size(); i++) {
            Set set = loadSet(list.get(i).getSetId());
            Tag tag = loadTag(set.getTagID());
            History4View history4View = new History4View(list.get(i), tag, 0);
            backList.add(history4View);
        }
        return backList;
    }


//    public List<History4View> loadMonthHistory() {
    //获取月的第一天和最后一天，在此基础上减一和加一，error:不止一天
    // PS:不要忘了first是endtime，last是begintime
    //for () try catch
    //写之前要分解
    //分块，职能专一
    //插入排序，从后插入

    //adapter：在begintime变化时，算差值，再插入title
    //异步加载？？？？？？
//    }


    public void deleteAboutTag(int tagId) {
        deleteTag(tagId);
        List<Integer> tagList = loadTagOrder();
        for (int i = 0; i<tagList.size(); i++) {
            if (tagList.get(i) == tagId)
                tagList.remove(i);
        }
        updateTagOrder(tagList);
        deleteActivity(deleteSet(tagId));
    }
    private void deleteTag(int tagId) {//and tagorder
        db.delete(Constant.TABLE_TAG, "id = ?", new String[] {"" + tagId});
    }

    private void deleteActivity(List<Integer> setIdList) {
        if (setIdList.size() == 0) {
            return;
        }
        for (int i = 0; i<setIdList.size(); i++) {
            db.delete(Constant.TABLE_ACTIVITY, Constant.ACTIVITY_SET_ID+ "=?",
                    new String[] {"" + setIdList.get(i)});
        }
    }
    private List<Integer> deleteSet(int tagId) {
        Cursor cursor = db.query(Constant.TABLE_SET, null,
                Constant.SET_TAG_ID+" = ?",
                new String []{""+tagId},
                null, null, null);
        List<Integer> setIdList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int setId = cursor.getInt(cursor.getColumnIndex("id"));
                setIdList.add(setId);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.delete(Constant.TABLE_SET, Constant.SET_TAG_ID + " = ?",
                new String[] {""+tagId});
        return setIdList;
    }
}
