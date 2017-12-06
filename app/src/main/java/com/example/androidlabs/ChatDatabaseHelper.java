package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 周星丞 on 10/18/2017.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Messages.db";
    public static final int VERSION_NUM = 2;
    public static final String MESSAGE_TABLE = "Message_Table";
    public static final String KEY_ID = "_id";
    public static final String KEY_MESSAGE ="Message";
    public static final String [] Column_Names = new String[]{
            KEY_ID,
            KEY_MESSAGE
    };
    private static final String CREATE_MESSAGE_TABLE = "create table "  + MESSAGE_TABLE  +
            " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_MESSAGE + " text ); " ;

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);

    }

    @Override
    public void onCreate(SQLiteDatabase db){

        Log.i("ChatDatabaseHelper", "Calling onCreate");
        db.execSQL(CREATE_MESSAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + "newVersion="+ newVer);
        db.execSQL("DROP TABLE IF EXISTS " + MESSAGE_TABLE);
        onCreate(db);

    }



}
