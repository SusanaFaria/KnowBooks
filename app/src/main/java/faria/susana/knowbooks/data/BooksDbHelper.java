package faria.susana.knowbooks.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import faria.susana.knowbooks.data.BooksContract.BookEntry;

public class BooksDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = BooksDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "books.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link BooksDbHelper}.
     *
     * @param context of the app
     */
    public BooksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the books_inventory table

        String SQL_CREATE_BOOKS_TABLE = "CREATE TABLE " + BookEntry.TABLE_NAME + " ("
                + BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BookEntry.COLUMN_BOOK_NAME + " TEXT NOT NULL, "
                + BookEntry.COLUMN_BOOK_AUTHOR + " TEXT NOT NULL, "
                + BookEntry.COLUMN_BOOK_PRICE + " INTEGER NOT NULL, "
                + BookEntry.COLUMN_BOOK_QUANTITY + " INTEGER NOT NULL, "
                + BookEntry.COLUMN_SUPPLIER + " TEXT NOT NULL, "
                + BookEntry.COLUMN_SUPPLIER_NUM + " TEXT NOT NULL );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_BOOKS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }

    /**
     * This is called when all entries in the table need to be deleted
     *
     * @param del
     */
    public void onDeleteAll(SQLiteDatabase del) {

        String SQL_DEL_ENTRY = "DELETE FROM " + BookEntry.TABLE_NAME;
        del.execSQL(SQL_DEL_ENTRY);
    }
}
