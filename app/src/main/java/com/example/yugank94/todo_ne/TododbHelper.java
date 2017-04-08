package com.example.yugank94.todo_ne;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yugank94 on 26/2/17.
 */

public class TododbHelper extends SQLiteOpenHelper {
    final static String TODO_TABLE_NAME= "Tododb";
    final static String TODO_COL_ID= "id";
    final static String TODO_COL_TITLE="title";
    final static String TODO_COL_DATE= "date";
    final static String TODO_COL_TIME= "time";
    final static String TODO_COL_PRIORITY= "priority";
    final static String TODO_COL_DESC= "description";

    public TododbHelper(Context context) {
        super(context,"TodoDatabase", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_query=  "CREATE TABLE "+TODO_TABLE_NAME+"( "+
                TODO_COL_ID+" INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, "+
                TODO_COL_TITLE+" VARCHAR(20) NOT NULL, "+
                TODO_COL_DATE+" VARCHAR(8) NOT NULL, "+
                TODO_COL_TIME+" VARCHAR(10), "+
                TODO_COL_PRIORITY+" VARCHAR(20) NOT NULL, "+
                TODO_COL_DESC+" VARCHAR(250) ); ";
        db.execSQL(sql_query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
