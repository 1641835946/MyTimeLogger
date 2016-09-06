package com.example.administrator.mytimelogger.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;

import com.example.administrator.mytimelogger.util.Constant;
import com.example.administrator.mytimelogger.R;

/**
 * Created by Administrator on 2016/8/22.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_TAG = "create table " +
            Constant.TABLE_TAG+ "(" +
            "id integer primary key autoincrement, " +
            Constant.TAG_NAME + " text, " +
            Constant.TAG_COLOR + " integer, " +
            Constant.TAG_ICON + " integer)";

    public static final String CREATE_SET = "create table " +
            Constant.TABLE_SET+ "(" +
            "id integer primary key autoincrement, " +
            Constant.SET_TAG_ID + " integer, " +
            Constant.SET_COMMIT + " text, " +
            Constant.SET_DURATION + " integer, " +
            Constant.SET_BEGIN_YEAR + " integer, " +
            Constant.SET_BEGIN_MONTH + " integer, " +
            Constant.SET_BEGIN_DAY + " integer, " +
            Constant.SET_BEGIN_HOUR + " integer, " +
            Constant.SET_BEGIN_MINUTE + " integer, " +
            Constant.SET_BEGIN_SECOND + " integer)";

    public static final String CREATE_ACTIVITY = "create table " +
            Constant.TABLE_ACTIVITY + "(" +
            "id integer primary key autoincrement, " +
            Constant.ACTIVITY_SET_ID + " integer, " +
            Constant.ACTIVITY_BEGIN_YEAR + " integer, " +
            Constant.ACTIVITY_BEGIN_MONTH + " integer, " +
            Constant.ACTIVITY_BEGIN_DAY + " integer, " +
            Constant.ACTIVITY_BEGIN_HOUR + " integer, " +
            Constant.ACTIVITY_BEGIN_MINUTE + " integer, " +
            Constant.ACTIVITY_BEGIN_SECOND + " integer, " +
            Constant.ACTIVITY_END_YEAR + " integer, " +
            Constant.ACTIVITY_END_MONTH + " integer, " +
            Constant.ACTIVITY_END_DAY + " integer, " +
            Constant.ACTIVITY_END_HOUR + " integer, " +
            Constant.ACTIVITY_END_MINUTE + " integer, " +
            Constant.ACTIVITY_END_SECOND + " integer, " +
            Constant.ACTIVITY_DURATION + " integer)";

    public static final String CREATE_SET_ORDER = "create table " +
            Constant.TABLE_SET_ORDER + "(" +
            "id integer primary key autoincrement, " +
            Constant.ORDER_SET_STATE + " integer, " +
            Constant.ORDER_SET_ID + " integer)";

    public static final String CREATE_TAG_ORDER = "create table " +
            Constant.TABLE_TAG_ORDER + "(" +
            "id integer primary key autoincrement, " +
            Constant.ORDER_TAG_ID + " integer)";

//    public static final String CREATE_ICON = "create table " +
//            Constant.TABLE_ICON +"(" +
//            "id integer primary key autoincrement, " +
//            Constant.ICON_RESID + " integer)";

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static final String TAG = "DBOpenHelper";
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "create activity: " + CREATE_ACTIVITY);
        Log.e(TAG, "create tag order: " + CREATE_SET_ORDER);
        Log.e(TAG, "create set order: " + CREATE_TAG_ORDER);
        Log.e(TAG, "create set: " + CREATE_SET);
        Log.e(TAG, "create tag: " + CREATE_TAG);
        db.execSQL(CREATE_ACTIVITY);
        db.execSQL(CREATE_TAG);
        db.execSQL(CREATE_SET);
        db.execSQL(CREATE_SET_ORDER);
        db.execSQL(CREATE_TAG_ORDER);
        String insertTag = "insert into " + Constant.TABLE_TAG + " (" +
                Constant.TAG_NAME + "," +
                Constant.TAG_ICON + "," +
                Constant.TAG_COLOR +
                ") values(?,?,?)";
        db.execSQL(insertTag, new String[]{"学习", ""+R.drawable.st_022, "" + R.color.colorAccent});
        db.execSQL(insertTag, new String[]{"阅读", "" + R.drawable.cat_96, "" + R.color.colorPrimary});
        db.execSQL(insertTag, new String[]{"家务", "" + R.drawable.hpcd_079, "" + R.color.colorPrimaryDark});
        db.execSQL(insertTag, new String[]{"运动", "" + R.drawable.sp_071, "" + R.color.deepPink});
        db.execSQL(insertTag, new String[]{"用餐", "" + R.drawable.fam_010, "" + R.color.lightPink});
        db.execSQL(insertTag, new String[]{"娱乐", "" + R.drawable.fam_021, "" + R.color.orchid});
        db.execSQL(insertTag, new String[]{"购物", "" + R.drawable.fs_002, "" + R.color.colorPrimary});
        db.execSQL(insertTag, new String[]{"步行", "" + R.drawable.fam_043, "" + R.color.colorPrimary});
        db.execSQL(insertTag, new String[]{"上网", "" + R.drawable.cat_107, "" + R.color.colorPrimary});
        db.execSQL(insertTag, new String[]{"交通", "" + R.drawable.tt_040, "" + R.color.colorPrimary});
        String insertTagOrder = "insert into " + Constant.TABLE_TAG_ORDER + " (" +
                Constant.ORDER_TAG_ID + ") values(?)";
        db.execSQL(insertTagOrder, new String[]{""+1});
        db.execSQL(insertTagOrder, new String[]{""+2});
        db.execSQL(insertTagOrder, new String[]{""+3});
        db.execSQL(insertTagOrder, new String[]{""+4});
        db.execSQL(insertTagOrder, new String[]{""+5});
        db.execSQL(insertTagOrder, new String[]{""+6});
        db.execSQL(insertTagOrder, new String[]{""+7});
        db.execSQL(insertTagOrder, new String[]{""+8});
        db.execSQL(insertTagOrder, new String[]{""+9});
        db.execSQL(insertTagOrder, new String[]{""+10});
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
}
