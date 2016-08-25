package com.example.administrator.mytimelogger.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.mytimelogger.model.Activities;
import com.example.administrator.mytimelogger.model.ActivitiesCustom;
import com.example.administrator.mytimelogger.model.GridTag;
import com.example.administrator.mytimelogger.model.Set;
import com.example.administrator.mytimelogger.model.Tag;
import com.example.administrator.mytimelogger.model.Time;
import com.example.administrator.mytimelogger.util.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public void saveTag(Tag tag) {
        if (tag != null) {
            ContentValues values = new ContentValues();
            values.put(Constant.TAG_NAME, tag.getName());
            values.put(Constant.TAG_COLOR, tag.getColor());
            values.put(Constant.TAG_ICON, tag.getIcon());
            db.insert(Constant.TABLE_TAG, null, values);
//            if (tag.getName() == null) Log.e(TAG, "saveTag: tag.getName = null");
//            else Log.e(TAG, "saveTag: tag.getName = null");
        }
    }

    //保存群组（含一个或多个活动）
    public void saveSet(Set set) {
        if (set != null) {
            ContentValues values = new ContentValues();
            //autoincrement id是自增长的，需要读取但不需要保存。
            values.put(Constant.SET_STATE, set.getState());
            values.put(Constant.SET_TAG_ID, set.getTagID());
            values.put(Constant.SET_COMMIT, set.getCommit());
            values.put(Constant.SET_DURATION, set.getDuration());
            values.put(Constant.SET_BEGIN_YEAR, set.getBeginTime().getYear());
            values.put(Constant.SET_BEGIN_MOUTH, set.getBeginTime().getMouth());
            values.put(Constant.SET_BEGIN_DAY, set.getBeginTime().getDay());
            values.put(Constant.SET_BEGIN_HOUR, set.getBeginTime().getHour());
            values.put(Constant.SET_BEGIN_MINUTE, set.getBeginTime().getMinute());
            values.put(Constant.SET_BEGIN_SECOND, set.getBeginTime().getSecond());
            db.insert(Constant.TABLE_SET, null, values);
        }
    }

    //保存活动（Activities区别于android中的Activity）
    public void saveActivities(Activities activities) {
        if (activities != null) {
            ContentValues values = new ContentValues();
            values.put(Constant.ACTIVITY_SET_ID, activities.getSetId());
            values.put(Constant.ACTIVITY_DURATION, activities.getDuration());
            values.put(Constant.ACTIVITY_BEGIN_YEAR, activities.getBeginTime().getYear());
            values.put(Constant.ACTIVITY_BEGIN_MOUTH, activities.getBeginTime().getMouth());
            values.put(Constant.ACTIVITY_BEGIN_DAY, activities.getBeginTime().getDay());
            values.put(Constant.ACTIVITY_BEGIN_HOUR, activities.getBeginTime().getHour());
            values.put(Constant.ACTIVITY_BEGIN_MINUTE, activities.getBeginTime().getMinute());
            values.put(Constant.ACTIVITY_BEGIN_SECOND, activities.getBeginTime().getSecond());
            values.put(Constant.ACTIVITY_BEGIN_YEAR, activities.getEndTime().getYear());
            values.put(Constant.ACTIVITY_BEGIN_MOUTH, activities.getEndTime().getMouth());
            values.put(Constant.ACTIVITY_BEGIN_DAY, activities.getEndTime().getDay());
            values.put(Constant.ACTIVITY_BEGIN_HOUR, activities.getEndTime().getHour());
            values.put(Constant.ACTIVITY_BEGIN_MINUTE, activities.getEndTime().getMinute());
            values.put(Constant.ACTIVITY_BEGIN_SECOND, activities.getEndTime().getSecond());
            db.insert(Constant.TABLE_ACTIVITY, null, values);
        }
    }

    //添加标签
    public void addTagOrder(int tagId) {
        ContentValues values = new ContentValues();
        values.put(Constant.ORDER_TAG_ID, tagId);
        db.insert(Constant.TABLE_ORDER, null, values);
    }

    //更新标签顺序
    public void updateTagOrder(List<Integer> tagIdList) {
        if (tagIdList != null && tagIdList.size() > 0) {
            db.delete(Constant.TABLE_ORDER, null, null);
            ContentValues values = new ContentValues();
            for (int i = 0; i < tagIdList.size(); i++) {
                values.put("id", i+1+"");
                values.put(Constant.ORDER_TAG_ID, tagIdList.get(i));
                db.insert(Constant.TABLE_ORDER, null, values);
                values.clear();
            }
        }
    }

    //加载标签列表（有顺序）
    public List<Integer> loadTagOrder() throws Resources.NotFoundException {
        boolean exist = false;
        Cursor cursor = db.query(Constant.TABLE_ORDER, null, null, null, null, null, null);
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

    //图标，名称，顺序
    public List<GridTag> loadGridTag() throws Resources.NotFoundException {
        List<Integer> tagList = loadTagOrder();
        List<GridTag> list = new ArrayList<>();
        GridTag gridTag = null;
        for (int i = 0; i < tagList.size(); i++) {
            gridTag = new GridTag();
            Tag tag =  loadTag(tagList.get(i));
            gridTag.setIcon(tag.getIcon());
            gridTag.setName(tag.getName());
            gridTag.setTagID(tag.getId());
            list.add(gridTag);
        }
        return list;
    }

    //加载自定义控件中活动的信息
    public List<ActivitiesCustom> loadNotEnded() throws Resources.NotFoundException {

        List<ActivitiesCustom> list = new ArrayList<>();
        List<Set> setList = new ArrayList<>();
        setList = loadNotEndedSet();
        list = set2ActivitiesCustom(setList);
        for (int i = 0; i < list.size(); i++) {
            Tag tag = loadTag(list.get(i).getSetID());
            list.get(i).setName(tag.getName());
            list.get(i).setIcon(tag.getIcon());
        }
        return list;
    }

    private List<ActivitiesCustom> set2ActivitiesCustom(List<Set> setList) {
        List<ActivitiesCustom> list = new ArrayList<>();
//        if (setList != null && setList.size() >0) {
//        }
        for (int i = 0; i < setList.size(); i++) {
            list.set(i, set2ActivitiesCustom(setList.get(i)));
        }
        return list;
    }

    private ActivitiesCustom set2ActivitiesCustom(Set set) {
        ActivitiesCustom activity = new ActivitiesCustom();
        activity.setSetID(set.getSetID());
        activity.setDuration(set.getDuration());
        activity.setBeginTime(set.getBeginTime());
        activity.setState(set.getState());
        return activity;
    }

    //加载未完成的一列群组（群组是单数）的具体信息
    public List<Set> loadNotEndedSet() throws Resources.NotFoundException {
        boolean exist = false;
        Cursor cursor = db.query(Constant.TABLE_SET, null, Constant.SET_STATE + "!=?",
                new String[]{"" + Constant.STATE_STOP}, null, null, null);
        List<Set> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            exist = true;
            do {
                Set activity = new Set();
                activity.setState(cursor.getInt(cursor.getColumnIndex(Constant.SET_STATE)));
                Time beginTime = new Time();
                beginTime.setYear(cursor.getInt(cursor.getColumnIndex(Constant.SET_BEGIN_YEAR)));
                beginTime.setMouth(cursor.getInt(cursor.getColumnIndex(Constant.SET_BEGIN_MOUTH)));
                beginTime.setDay(cursor.getInt(cursor.getColumnIndex(Constant.SET_BEGIN_DAY)));
                beginTime.setHour(cursor.getInt(cursor.getColumnIndex(Constant.SET_BEGIN_HOUR)));
                beginTime.setMinute(cursor.getInt(cursor.getColumnIndex(Constant.SET_BEGIN_MINUTE)));
                beginTime.setSecond(cursor.getInt(cursor.getColumnIndex(Constant.SET_BEGIN_SECOND)));
                activity.setBeginTime(beginTime);
                activity.setDuration(cursor.getLong(cursor.getColumnIndex(Constant.SET_DURATION)));
                activity.setTagID(cursor.getInt(cursor.getColumnIndex(Constant.SET_TAG_ID)));
                activity.setSetID(cursor.getInt(cursor.getColumnIndex("id")));
                list.add(activity);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        if (exist) {return list;}
        else {throw new Resources.NotFoundException();}
    }
    //加载标签的具体信息
    public Tag loadTag(int tagId) throws Resources.NotFoundException {
        boolean exist = false;
        Cursor cursor = db.query(Constant.TABLE_TAG, null, "id = ?",
                new String[] {String.valueOf(tagId)}, null, null, null);
        Tag tag = new Tag();
        if (cursor.moveToFirst()) {
            exist = true;
            do {
                tag = new Tag();
                tag.setId(tagId);
                tag.setName(cursor.getString(cursor.getColumnIndex(Constant.TAG_NAME)));
                tag.setColor(cursor.getLong(cursor.getColumnIndex(Constant.TAG_COLOR)));
                tag.setIcon(cursor.getLong(cursor.getColumnIndex(Constant.TAG_ICON)));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        if (exist) {return tag;}
        else {throw new Resources.NotFoundException();}
    }
}
