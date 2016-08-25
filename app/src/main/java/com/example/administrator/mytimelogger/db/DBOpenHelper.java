package com.example.administrator.mytimelogger.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.administrator.mytimelogger.util.Constant;
import com.example.administrator.mytimelogger.R;

/**
 * Created by Administrator on 2016/8/22.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_TAG = "create table Tag (" +
            "id integer primary key autoincrement, " +
            Constant.TAG_NAME + " text, " +
            Constant.TAG_COLOR + " integer, " +
            Constant.TAG_ICON + " integer)";

    public static final String CREATE_SET = "create table Set (" +
            "id integer primary key autoincrement, " +
            Constant.SET_STATE + " integer, " +
            Constant.SET_TAG_ID + " integer, " +
            Constant.SET_COMMIT + " text, " +
            Constant.SET_DURATION + " integer, " +
            Constant.SET_BEGIN_YEAR + " integer, " +
            Constant.SET_BEGIN_MOUTH + " integer, " +
            Constant.SET_BEGIN_DAY + " integer, " +
            Constant.SET_BEGIN_HOUR + " integer, " +
            Constant.SET_BEGIN_MINUTE + " integer, " +
            Constant.SET_BEGIN_SECOND + " integer)";

    public static final String CREATE_ACTIVITY = "create table Activity (" +
            "id integer primary key autoincrement, " +
            Constant.ACTIVITY_SET_ID + "integer, " +
            Constant.ACTIVITY_BEGIN_YEAR + " integer, " +
            Constant.ACTIVITY_BEGIN_MOUTH + " integer, " +
            Constant.ACTIVITY_BEGIN_DAY + " integer, " +
            Constant.ACTIVITY_BEGIN_HOUR + " integer, " +
            Constant.ACTIVITY_BEGIN_MINUTE + " integer, " +
            Constant.ACTIVITY_BEGIN_SECOND + " integer, " +
            Constant.ACTIVITY_END_YEAR + " integer, " +
            Constant.ACTIVITY_END_MOUTH + " integer, " +
            Constant.ACTIVITY_END_DAY + " integer, " +
            Constant.ACTIVITY_END_HOUR + " integer, " +
            Constant.ACTIVITY_END_MINUTE + " integer, " +
            Constant.ACTIVITY_END_SECOND + " integer, " +
            Constant.ACTIVITY_DURATION + " integer)";

    public static final String CREATE_ORDER = "create table Order (" +
            "id integer primary key autoincrement, " +
            Constant.ORDER_TAG_ID + " integer)";

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static final String TAG = "DBOpenHelper";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACTIVITY);
        db.execSQL(CREATE_ORDER);
        db.execSQL(CREATE_SET);
        db.execSQL(CREATE_TAG);
        Log.e(TAG, "create activity: " + CREATE_ACTIVITY);
        Log.e(TAG, "create order: " + CREATE_ORDER);
        Log.e(TAG, "create set: " + CREATE_SET);
        Log.e(TAG, "create tag: " + CREATE_TAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
}
