package faria.susana.knowbooks;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import faria.susana.knowbooks.data.BooksContract.BookEntry;
import faria.susana.knowbooks.data.BooksDbHelper;

public class BookCatalog extends AppCompatActivity {
    private BooksDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookCatalog.this, BookEditor.class);
                startActivity(intent);
            }
        });
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new BooksDbHelper(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the books database.
     */
    private void displayDatabaseInfo() {

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {BookEntry._ID,
                BookEntry.COLUMN_BOOK_NAME,
                BookEntry.COLUMN_BOOK_AUTHOR,
                BookEntry.COLUMN_BOOK_PRICE,
                BookEntry.COLUMN_BOOK_QUANTITY,
                BookEntry.COLUMN_SUPPLIER,
                BookEntry.COLUMN_SUPPLIER_NUM};


        Cursor c = db.query(BookEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = (TextView) findViewById(R.id.booksText);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The BOOKS table contains <number of rows in Cursor> books.
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText(getString(R.string.inventory_contains) + c.getCount() + getString(R.string.books));
            displayView.append(BookEntry._ID + " - " +
                    BookEntry.COLUMN_BOOK_NAME + "-" +
                    BookEntry.COLUMN_BOOK_AUTHOR + "-" +
                    BookEntry.COLUMN_BOOK_PRICE + "-" +
                    BookEntry.COLUMN_BOOK_QUANTITY + "-" +
                    BookEntry.COLUMN_SUPPLIER + "-" +
                    BookEntry.COLUMN_SUPPLIER_NUM + "\n");

            // Figure out the index of each column
            int idColumnIndex = c.getColumnIndex(BookEntry._ID);
            int bookColumnIndex = c.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
            int authorColumnIndex = c.getColumnIndex(BookEntry.COLUMN_BOOK_AUTHOR);
            int priceColumnIndex = c.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
            int quantityColumnIndex = c.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);
            int supplierColumnIndex = c.getColumnIndex(BookEntry.COLUMN_SUPPLIER);
            int supplierNumColumnIndex = c.getColumnIndex(BookEntry.COLUMN_SUPPLIER_NUM);


            // Iterate through all the returned rows in the cursor
            while (c.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = c.getInt(idColumnIndex);
                String currentBook = c.getString(bookColumnIndex);
                String currentAuthor = c.getString(authorColumnIndex);
                int currentPrice = c.getInt(priceColumnIndex);
                int currentQuantity = c.getInt(quantityColumnIndex);
                String currentSupplier = c.getString(supplierColumnIndex);
                int currentSupplierNum = c.getInt(supplierNumColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentBook + " - " +
                        currentAuthor + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplier + " - " +
                        currentSupplierNum));

            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            c.close();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_book_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                SQLiteDatabase db = mDbHelper.getReadableDatabase();
                mDbHelper.onDeleteAll(db);
                displayDatabaseInfo();
        }

        return super.onOptionsItemSelected(item);
    }
}


