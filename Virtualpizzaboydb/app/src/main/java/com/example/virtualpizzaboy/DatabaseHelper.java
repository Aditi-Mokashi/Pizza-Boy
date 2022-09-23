package com.example.virtualpizzaboy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
    SQLiteDatabase sqLiteDatabase;

    public DatabaseHelper(Context context)
    {
        super(context, "Wallet.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table wallet (uid text primary key, pin text, amount text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists wallet");
    }

    public Boolean insertData(String uid, String pin, String amount)
    {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("uid", uid);
        contentValues.put("pin", pin);
        contentValues.put("amount", amount);

        return sqLiteDatabase.insert("wallet",null, contentValues) != -1;
    }


    public Boolean updateData(String uid, String pin, String amount)
    {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("uid", uid);
        contentValues.put("pin", pin);
        contentValues.put("amount", amount);

        Cursor cursor = sqLiteDatabase.rawQuery("select * from wallet where uid = ? ",
                new String[]{uid});

        if(cursor.getCount() > 0)
        {
            return sqLiteDatabase.update("wallet", contentValues, "uid=?",
                    new String[]{uid}) != -1;
        }
        else
        {
            return false;
        }
    }

    public Cursor getRecord(String uid) {
        sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("select * from wallet where uid = ? ", new String[]{uid});
    }
}