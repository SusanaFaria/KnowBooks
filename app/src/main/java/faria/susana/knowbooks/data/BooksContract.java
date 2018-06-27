package faria.susana.knowbooks.data;

import android.provider.BaseColumns;

public final class BooksContract {

    private BooksContract() {
    }

    public static final class BookEntry implements BaseColumns {


        public final static String TABLE_NAME = "Books_Inventory";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_BOOK_NAME = "Book_Name";

        public final static String COLUMN_BOOK_AUTHOR = "Author";

        public final static String COLUMN_BOOK_PRICE = "Price";

        public final static String COLUMN_BOOK_QUANTITY = "InStock_Quantity";

        public final static String COLUMN_SUPPLIER = "Supplier_Name";

        public final static String COLUMN_SUPPLIER_NUM = "Supplier_Num";

    }

}



