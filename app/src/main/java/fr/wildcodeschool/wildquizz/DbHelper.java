package fr.wildcodeschool.wildquizz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wilder on 04/04/18.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String SQL_CREATE_QCM_ENTRIES =
            "CREATE TABLE " + DbContract.QcmEntry.TABLE_NAME + " (" +
                    DbContract.QcmEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DbContract.QcmEntry.COLUMN_NAME_QCM + " TEXT)";


    public static final String SQL_DELETE_QCM_ENTRIES =
            "DROP TABLE IF EXISTS " + DbContract.QcmEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "database.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_QCM_ENTRIES);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_QCM_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
