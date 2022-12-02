package com.ass2.project;

import android.provider.BaseColumns;

public class dataContract {
    public static String DB_NAME="financiio.db";
    public static int DB_VERSION=1;

    //this class acts as a table
    public static class Data implements BaseColumns {
        public static String TABLE_NAME="contactsTable";
        public static String _ITEM_ID = "itemID";
        public static String _AMOUNT = "amount";
        public static String _CATEGORY = "category";
        public static String _DESCRIPTION = "description";
        public static String _TIME = "time";
        public static String _IMAGE = "image";
    }
}
