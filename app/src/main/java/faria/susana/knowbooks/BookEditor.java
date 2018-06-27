package faria.susana.knowbooks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import faria.susana.knowbooks.data.BooksContract.BookEntry;
import faria.susana.knowbooks.data.BooksDbHelper;

/**
 * Allows user to create a new book or edit an existing one.
 */
public class BookEditor extends AppCompatActivity {


    private EditText mBookNameEditText;

    private EditText mBookAuthorEditText;

    private EditText mBookPriceEditText;

    private EditText mBookQuantity;

    private EditText mBookSupplier;

    private EditText mBookSupplierNum;

    private BooksDbHelper mDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_editor);


        // Find all relevant views that we will need to read user input from
        mBookNameEditText = (EditText) findViewById(R.id.edit_book_name);
        mBookAuthorEditText = (EditText) findViewById(R.id.edit_book_author);
        mBookPriceEditText = (EditText) findViewById(R.id.edit_book_price);
        mBookQuantity = (EditText) findViewById(R.id.edit_book_quantity);
        mBookSupplier = (EditText) findViewById(R.id.edit_book_supplier);
        mBookSupplierNum = (EditText) findViewById(R.id.edit_book_supplier_num);
        mDbHelper = new BooksDbHelper(this);
    }

    /**
     * Get user input from editor and save new book into database.
     */
    private void insertBook() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String bookName = mBookNameEditText.getText().toString().trim();
        String authorName = mBookAuthorEditText.getText().toString().trim();
        String priceString = mBookPriceEditText.getText().toString().trim();
        double price = Double.parseDouble(priceString);
        String quantityString = mBookQuantity.getText().toString().trim();
        double quantity = Double.parseDouble(quantityString);
        String supplierName = mBookSupplier.getText().toString().trim();
        String supplierNumString = mBookSupplierNum.getText().toString().trim();
        long supplierNum = Long.parseLong(supplierNumString);


        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_BOOK_NAME, bookName);
        values.put(BookEntry.COLUMN_BOOK_AUTHOR, authorName);
        values.put(BookEntry.COLUMN_BOOK_PRICE, price);
        values.put(BookEntry.COLUMN_BOOK_QUANTITY, quantity);
        values.put(BookEntry.COLUMN_SUPPLIER, supplierName);
        values.put(BookEntry.COLUMN_SUPPLIER_NUM, supplierNum);

        // Insert a new row for books in the database, returning the ID of that new row.
        long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving book", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Book saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_book_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_book_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Done" button in the app Bar
            case R.id.action_save:
                // Save book to database
                insertBook();
                // Exit activity and go back to catalog Activity
                finish();
                // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Home" button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);

        }
        return super.onOptionsItemSelected(item);
    }

}

