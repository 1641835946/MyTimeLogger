package com.example.administrator.mytimelogger.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.mytimelogger.model.Activities;
import com.example.administrator.mytimelogger.model.CustomSet4View;
import com.example.administrator.mytimelogger.model.Set;
import com.example.administrator.mytimelogger.model.SetItemInOrder;
import com.example.administrator.mytimelogger.model.Tag;
import com.example.administrator.mytimelogger.model.Time;
import com.example.administrator.mytimelogger.util.Constant;

import java.util.ArrayList;
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
            values.put(Constant.SET_BEGIN_MOUTH, set.getBeginTime().getMouth());
            values.put(Constant.SET_BEGIN_DAY, set.getBeginTime().getDay());
            values.put(Constant.SET_BEGIN_HOUR, set.getBeginTime().getHour());
            values.put(Constant.SET_BEGIN_MINUTE, set.getBeginTime().getMinute());
            values.put(Constant.SET_BEGIN_SECOND, set.getBeginTime().getSecond());
            db.insert(Constant.TABLE_SET, null, values);
            Cursor cursor = db.query(Constant.TABLE_SET, null,
                    Constant.SET_BEGIN_YEAR + " = ? and " +
                            Constant.SET_BEGIN_MOUTH + " = ? and " +
                            Constant.SET_BEGIN_DAY + " = ? and " +
                            Constant.SET_BEGIN_HOUR + " = ? and " +
                            Constant.SET_BEGIN_MINUTE + " = ? and " +
                            Constant.SET_BEGIN_SECOND + " = ?",
                    new String[] {""+set.getBeginTime().getYear(),
                            ""+set.getBeginTime().getMouth(),
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

//    //添加标签//直接更新标签顺序，id才不会错
//    public void addTagOrder(int tagId) {
//        ContentValues values = new ContentValues();
//        values.put(Constant.ORDER_TAG_ID, tagId);
//        db.insert(Constant.TABLE_ORDER, null, values);
//    }

    //更新标签顺序
    public void updateTagOrder(List<Integer> tagIdList) {
        if (tagIdList != null && tagIdList.size() > 0) {
            db.delete(Constant.TABLE_TAG_ORDER, null, null);
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
        if (setList != null && setList.size() > 0) {
            db.delete(Constant.TABLE_SET_ORDER, null, null);
            ContentValues values = new ContentValues();
            for (int i = 0; i < setList.size(); i++) {
                values.put("id", i+1+"");
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

    //加载未完成的一列群组（群组是单数）的具体信息
    public List<CustomSet4View> loadSetListNotEnded() throws Resources.NotFoundException {
        boolean exist = false;
        List<CustomSet4View> list = new ArrayList<>();
        try {
            List<SetItemInOrder> setItemInOrderList = loadSetOrder();
            exist = true;
            for (int i = 0; i<setItemInOrderList.size(); i++) {
                CustomSet4View customSet4View = new CustomSet4View();
                int setID = setItemInOrderList.get(i).getSetID();
                Set set = loadSet(setID);
                Tag tag = loadTag(set.getTagID());
                String tagName = tag.getName();
                int tagIcon = tag.getIcon();
                customSet4View.setTagIcon(tagIcon);
                customSet4View.setSet(set);
                customSet4View.setState(setID);
                customSet4View.setTagName(tagName);
                list.add(customSet4View);
            }
        } catch (Resources.NotFoundException e) {}
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
                tag.setColor(cursor.getLong(cursor.getColumnIndex(Constant.TAG_COLOR)));
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
                set = new Set();
                set.setSetID(setId);
                set.setTagID(cursor.getInt(cursor.getColumnIndex(Constant.SET_TAG_ID)));
                Time beginTime = new Time();
                beginTime.setYear(cursor.getInt(cursor.getColumnIndex(Constant.SET_BEGIN_YEAR)));
                beginTime.setMouth(cursor.getInt(cursor.getColumnIndex(Constant.SET_BEGIN_MOUTH)));
                beginTime.setDay(cursor.getInt(cursor.getColumnIndex(Constant.SET_BEGIN_DAY)));
                beginTime.setHour(cursor.getInt(cursor.getColumnIndex(Constant.SET_BEGIN_HOUR)));
                beginTime.setMinute(cursor.getInt(cursor.getColumnIndex(Constant.SET_BEGIN_MINUTE)));
                beginTime.setSecond(cursor.getInt(cursor.getColumnIndex(Constant.SET_BEGIN_SECOND)));
                set.setBeginTime(beginTime);
                set.setCommit(cursor.getString(cursor.getColumnIndex(Constant.SET_COMMIT)));
                set.setDuration(cursor.getLong(cursor.getColumnIndex(Constant.SET_DURATION)));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        if (exist) {return set;}
        else {throw new Resources.NotFoundException();}
    }
}
