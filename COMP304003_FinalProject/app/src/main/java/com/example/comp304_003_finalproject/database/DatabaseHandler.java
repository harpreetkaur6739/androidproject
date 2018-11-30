package com.example.comp304_003_finalproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.example.comp304_003_finalproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHandler {

    private static DatabaseManager dbManager;

    private static DatabaseHandler dbHandler;

    private static String tables[];
    //
    private static String tableCreatorString[];

    private DatabaseHandler(){}

    public DatabaseHandler(Context context){
        tables = context.getResources().getStringArray(R.array.tables);
        tableCreatorString = context.getResources().getStringArray(R.array.table_create_scripts);

        dbManager = new DatabaseManager(context);
        dbManager.dbInitialize(tables,tableCreatorString);
    }

    public static DatabaseHandler getDbHandlerInstance(){
        if(dbHandler == null){
            dbHandler = new DatabaseHandler();

        }
        return dbHandler;
    }

    /////////////////////////
    // Database operations
    /////////////////////////
    // Add a new record
    public long addRecord(ContentValues values, String tableName, String fields[], String record[]) {
        SQLiteDatabase db = dbManager.getWritableDbHandler();

        for (int i=1;i<record.length;i++)
            values.put(fields[i], record[i]);
        // Insert the row
        long rowId = db.insert(tableName, null, values);
        db.close(); //close database connection
        return rowId;
    }


    // Read all records
    public List getTable(String tableName) {
        List table = new ArrayList(); //to store all rows
        // Select all records
        String selectQuery = "SELECT  * FROM " + tableName;

        SQLiteDatabase db = dbManager.getReadableDbHandler();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //scroll over rows and store each row in an array list object
        if (cursor.moveToFirst())
        {
            do
            { // for each row
                ArrayList row=new ArrayList(); //to store one row

                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    row.add(cursor.getString(i));
                }

                table.add(row); //add row to the list

            } while (cursor.moveToNext());
        }

        // return table as a list
        return table;
    }

    // Read all records and return cursor
    public Cursor getTableCursor(String tableName,String[] keyName, String[] value) {

        String selectQuery = "SELECT  * FROM " + tableName;

        SQLiteDatabase db = dbManager.getReadableDbHandler();

        int count = 0;
        if(keyName != null && keyName.length > count){
            selectQuery += " WHERE ";
            while(keyName.length > count){
                selectQuery += keyName[count] + " = ? ";
                count++;
                if(keyName.length > count){
                    selectQuery += " AND ";
                }
            }

        }
        Cursor cursor = db.rawQuery(selectQuery, value);

        return cursor;
    }

    // Read all records and return cursor
    public Cursor getJoinTableCursor(String tableName1, String tableName2, String matchTab1, String matchTab2, String tableCondition, String[] keyName, String[] value) {

        String selectQuery = "SELECT  * FROM " + tableName1 + " ta JOIN " + tableName2 + " tb ON ta." + matchTab1 + " = tb." + matchTab2;

        SQLiteDatabase db = dbManager.getReadableDbHandler();

        int count = 0;
        if(keyName != null && keyName.length > count){

            String tConditionAlias = "ta";
            if(tableName2.equals(tableCondition)){
                tConditionAlias = "tb";
            }

            selectQuery += " WHERE ";
            while(keyName.length > count){
                selectQuery += tConditionAlias + "." + keyName[count] + " = ? ";
                count++;
                if(keyName.length > count){
                    selectQuery += " AND ";
                }
            }

        }
        Cursor cursor = db.rawQuery(selectQuery, value);

        return cursor;
    }


    // Update a record
    public int updateRecord(ContentValues values, String tableName, String fields[],String record[]) {
        SQLiteDatabase db =dbManager.getWritableDbHandler();

        for (int i=1;i<record.length;i++)
            values.put(fields[i], record[i]);

        // updating row with given id = record[0]
        return db.update(tableName, values, fields[0] + " = ?",
                new String[] { record[0] });
    }

    // Delete a record with a given id
    public void deleteRecord(String tableName, String idName, String id) {
        SQLiteDatabase db = dbManager.getWritableDbHandler();
        db.delete(tableName, idName + " = ?",
                new String[] { id });
        db.close();
    }

    //Returns single record matching key and value
    public ArrayList getRecord(String tableName, String[] keyName, String[] value){
        SQLiteDatabase db = dbManager.getReadableDbHandler();
        ArrayList row=new ArrayList();
        String selectQuery = "SELECT  * FROM " + tableName;
        int count = 0;
        if(keyName.length > count){
            selectQuery += " WHERE ";
            while(keyName.length > count){
                selectQuery += keyName[count] + " = ? ";
                count++;
                if(keyName.length > count){
                    selectQuery += " AND ";
                }
            }

        }
        Cursor cursor = db.rawQuery(selectQuery, value);

        if (cursor.moveToFirst())
        {
            do
            { // for each row
                for (int i =0 ; i< cursor.getColumnCount(); i++)
                {
                    String data = cursor.getString(i);
                    String column_name = cursor.getColumnName(i);

                    HashMap<String,String> map = new HashMap<String,String>();
                    map.put("column_value",data);
                    map.put("column_name",column_name);

                    row.add(map); //change the type of details from ArrayList<String> to arrayList<HashMap<String,String>>
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        return row;
    }
}


