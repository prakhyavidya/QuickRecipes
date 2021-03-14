package com.example.project;

import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import static android.provider.BaseColumns._ID;

public class MySqlLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String RECIPE_COLUMN_ID = "id";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_MATERIALS = "materials";
    public static final String RECIPE_INSTRUCTIONS = "instructions";
    private HashMap hp;

    public MySqlLiteHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table recipe " + "(id integer primary key , name text , materials text , instructions text )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recipe");
        onCreate(db);

    }

    public boolean insertRecipe(String name, String materials, String instructions){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("materials", materials);
        contentValues.put("instructions", instructions);
        db.insert("recipe", null, contentValues);
        return true;
    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from recipe where id="+id+"", null );
        return res;
    }
    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, RECIPE_NAME);
        return numRows;
    }
    public boolean updateRecipe (Integer id, String name, String materials, String instructions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("materials", materials);
        contentValues.put("instructions", instructions);
        db.update("recipe", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public Integer deleteRecipe (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("recipe",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
    public ArrayList<String> getAllRecipes() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from recipe", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(RECIPE_NAME)));
            res.moveToNext();
        }
        return array_list;
    }



}
