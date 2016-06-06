package com.example.xiedongdong.app02.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xiedongdong on 16/5/31.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     *
     * @param context  上下文的意思，必须有他才可对数据库进行操作
     * @param name     数据库的名字，
     * @param factory  允许我们在查询数据库时返回一个自定义的cursor，一般传入null
     * @param version  数据库的版本号
     */

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static final String CREATE_USER="create table user (" +
            "id integer primary key autoincrement," +
            "username text," +
            "password text," +
            "email text)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){

        }

    }

}
