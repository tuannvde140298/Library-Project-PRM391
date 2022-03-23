package com.example.library_project.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.library_project.data.models.Book;
import com.example.library_project.data.models.Favorite;

import java.util.ArrayList;
import java.util.List;

public class DB_Handler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Library";

    private static String WishListTable = "favorite";

    private static final String ID = "id";

    private static final String IS_HEART = "is_heart";
    private static final String ID_BOOK = "book_id";

    // Create Wish List Table
    private static final String CREATE_WISHLIST_TABLE = "CREATE TABLE " + WishListTable + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + IS_HEART + " INTEGER NOT NULL,"
            + ID_BOOK + " INTEGER NOT NULL" + ")";

    public DB_Handler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WISHLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WishListTable);
        // Create tables again
        onCreate(db);
    }

    public long addFavorite(String bookId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_BOOK, bookId);
        return db.insert(WishListTable, null, values);
    }

    public List<Favorite> getListFavorite() {
        List<Favorite> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + WishListTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new Favorite(cursor.getInt(0), cursor.getInt(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return list;
    }

    public boolean removeFavorite(int bookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(WishListTable, ID_BOOK + " = ?", new String[]{String.valueOf(bookId)}) > 0;
    }
}
