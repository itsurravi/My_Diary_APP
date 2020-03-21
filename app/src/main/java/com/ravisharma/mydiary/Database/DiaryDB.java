package com.ravisharma.mydiary.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ravisharma.mydiary.Prefrence.PrefManager;

public class DiaryDB extends SQLiteOpenHelper {

    public static final String DB_NAME = "DiaryDB";
    public static final String TABLE_NAME = "MyDiary";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DATE = "date";
    public static final String ENTRY = "entry";


    private static final int DB_VERSION = 1;
    Context c;

    public DiaryDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " VARCHAR, "
                + DATE + " VARCHAR, "
                + ENTRY + " VARCHAR);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public void addEntry(String Date, String Entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        PrefManager pf = new PrefManager(c);
        String name = pf.getUserName();

        contentValues.put(NAME, name);
        contentValues.put(DATE, Date);
        contentValues.put(ENTRY, Entry);

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public Cursor getEntries() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + ID + " DESC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

}
