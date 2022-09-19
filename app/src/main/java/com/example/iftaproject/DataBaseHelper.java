package com.example.iftaproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String RECEIPT_TABLE = "RECEIPT_TABLE";

    public static final String RECEIPT_DATA = "RECEIPT_DATA";
    public static final String RECEIPT_DATE = "RECEIPT_DATE";
    public static final String RECEIPT_IMAGEURI = "RECEIPT_IMAGEURI";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "receipts.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + RECEIPT_TABLE + " (" + RECEIPT_DATA + " TEXT, " + " TEXT, " + RECEIPT_DATE + " TEXT, " + RECEIPT_IMAGEURI + " TEXT) ";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public boolean addOne(receipt receipt1){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(RECEIPT_DATA, receipt1.getData());
        cv.put(RECEIPT_DATE, receipt1.getDate());
        cv.put(RECEIPT_IMAGEURI, receipt1.getImageuri());

        long insert = db.insert(RECEIPT_TABLE, null, cv);

        if(insert == -1){
            return false;
        }else{
            return true;
        }

    }

    public List<receipt> getAllNotesFromLocalDB(){

        List<receipt> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + RECEIPT_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String receiptData = cursor.getString(0);
                String receiptTotal = cursor.getString(1);
                String receiptDate = cursor.getString(2);
                String receiptImageuri = cursor.getString(3);

                receipt newReceipt = new receipt(""+receiptData, ""+receiptDate, ""+receiptImageuri);

                returnList.add(newReceipt);
            } while (cursor.moveToNext());

        }else{
            //failed
        }


        cursor.close();
        db.close();
        return returnList;
    }


    //below needs work
    public void changeNoteInfo(String receiptType, String receiptTotal, String receiptDate){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + RECEIPT_TABLE + " SET " + RECEIPT_DATA + " = '" + receiptType +"' WHERE "
                + RECEIPT_DATA + " = '" + receiptDate + "'" ;

        String query2 = "UPDATE " + RECEIPT_TABLE + " SET " + " = '" + receiptTotal + "' WHERE "
                + RECEIPT_DATA + " = '" + receiptDate + "'";

        db.execSQL(query2);
        db.execSQL(query);
    }


    public ArrayList<String> getTitles(){
        ArrayList<String> titles = new ArrayList();

        String queryString = "SELECT " + RECEIPT_DATA + " FROM " + RECEIPT_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String noteTitle = cursor.getString(0);

                titles.add(noteTitle);

            } while (cursor.moveToNext());

        }else{
            //failed
        }

        cursor.close();
        db.close();
        return  titles;

    }

    public void deleteNote(String noteTitle){

        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(RECEIPT_TABLE, RECEIPT_DATA + "= '" + noteTitle + "'", null );


    }






}

