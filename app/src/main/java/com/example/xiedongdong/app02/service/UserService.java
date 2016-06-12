package com.example.xiedongdong.app02.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.xiedongdong.app02.db.DatabaseHelper;
import com.example.xiedongdong.app02.po.User;

/**
 * Created by xiedongdong on 16/6/1.
 */
public class UserService {
    private DatabaseHelper dbHelper;


    public UserService(Context context){
        dbHelper=new DatabaseHelper(context,"user.db",null,1);
    }


    public boolean insertUser(User user) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put("username",user.getUsername());
        values.put("password",user.getPassword());
        values.put("username",user.getEmail());

        db.insert("user",null,values);

        return true;
    }

    public boolean getUserByUsernamePassword(String txt_username,String txt_password) {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String sql="select username,password from user where username=? and password=?";
        Cursor cursor=db.rawQuery(sql,new String[]{txt_username,txt_password});
        if(cursor.moveToFirst()==true){
            cursor.close();
            return true;
        }

        return false;
    }

    public boolean getUserByUsername(String txt_newUsername) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String sql="select username from user where username=?";
        Cursor cursor=db.rawQuery(sql,new String[]{txt_newUsername});

        if(cursor.moveToFirst()==true){
            cursor.close();
            return true;
        }

        return false;
    }


    public boolean getUsernameByUpdatePassword(String txt_newPassword,String txt_currentUser ){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String sql="update user set password=? where username=?";
        db.execSQL(sql,new Object[]{txt_newPassword,txt_currentUser});

        return true;
    }
}
