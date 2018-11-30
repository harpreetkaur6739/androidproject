package com.example.comp304_003_finalproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.comp304_003_finalproject.R;

public class DatabaseManager extends SQLiteOpenHelper {
    //
    private static String DATABASE_NAME;
    private static int DATABASE_VERSION;
    //
    private String tables[]; //table names
    private String tableCreatorString[]; //SQL statements to create tables
    //class constructor
    public DatabaseManager(Context context) {

        //super(context, DATABASE_NAME, null, DATABASE_VERSION);
        super(context,  context.getString(R.string.db_name), null,  Integer.parseInt(context.getString(R.string.db_version)));
        DATABASE_NAME = context.getString(R.string.db_name);
        DATABASE_VERSION = Integer.parseInt(context.getString(R.string.db_version));
    }
    // Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Drop existing tables
        for (int i=0;i<tables.length;i++)
            db.execSQL("DROP TABLE IF EXISTS " + tables[i]);
        //create them
        for (int i=0;i<tables.length;i++)
            db.execSQL(tableCreatorString[i]);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables
        for (int i=0;i<tables.length;i++)
            db.execSQL("DROP TABLE IF EXISTS " + tables[i]);

        // Create tables again
        onCreate(db);
    }

    //create the database
    public void createDatabase(Context context)
    {
        SQLiteDatabase mDatabase;
        mDatabase = context.openOrCreateDatabase(
                DATABASE_NAME, SQLiteDatabase.CREATE_IF_NECESSARY, null);

    }

    //initialize database table names and DDL statements
    public void dbInitialize(String[] tables, String tableCreatorString[])
    {
        this.tables=tables;
        this.tableCreatorString=tableCreatorString;
    }

    public SQLiteDatabase getWritableDbHandler(){
        return this.getWritableDatabase();
    }

    public SQLiteDatabase getReadableDbHandler(){
        return this.getReadableDatabase();
    }

    //delete the database
    public void deleteDatabase(Context context)
    {
        context.deleteDatabase(DATABASE_NAME);
    }
}
