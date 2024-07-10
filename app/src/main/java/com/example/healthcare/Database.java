package com.example.healthcare;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "healthcare.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_CART = "cart";
    private static final String TABLE_ORDER_PLACE = "orderPlace";

    // Common column names
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    // Cart table column names
    private static final String COLUMN_PRODUCT = "product";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_OTYPE = "otype";

    // OrderPlace table column names

    private static final String COLUMN_FULLNAME = "fullname";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_CONTACTNO = "contactno";
    private static final String COLUMN_PINCODE = "pincode";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = String.format(
                "CREATE TABLE %s (%s TEXT, %s TEXT, %s TEXT)",
                TABLE_USERS, COLUMN_USERNAME, COLUMN_EMAIL, COLUMN_PASSWORD
        );
        db.execSQL(createUsersTable);

        String createCartTable = String.format(
                "CREATE TABLE %s (%s TEXT, %s TEXT, %s REAL, %s TEXT)",
                TABLE_CART, COLUMN_USERNAME, COLUMN_PRODUCT, COLUMN_PRICE, COLUMN_OTYPE
        );
        db.execSQL(createCartTable);

        String createOrderPlaceTable = String.format(
                "CREATE TABLE %s (%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s TEXT, %s TEXT, %s TEXT, %s REAL, %s TEXT)",
                TABLE_ORDER_PLACE, COLUMN_USERNAME, COLUMN_FULLNAME,COLUMN_ADDRESS, COLUMN_CONTACTNO,
                COLUMN_PINCODE, COLUMN_DATE, COLUMN_TIME, COLUMN_AMOUNT, COLUMN_OTYPE
        );
        db.execSQL(createOrderPlaceTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_USERS));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_CART));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_ORDER_PLACE));
        onCreate(db);
    }

    public void register(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public int login(String username, String password) {
        int result = 0;
        String str[] = new String[2];
        str[0] = username;
        str[1] = password;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = null;
        try {
            String query = "SELECT * FROM users WHERE username=? AND password=?";
            c = db.rawQuery(query, str);

            if (c != null && c.moveToFirst()) {
                result = 1; // Successful login
            } else {
                result = -1; // Invalid credentials
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = -2; // Error occurred
        } finally {
            if (c != null) {
                c.close();
            }
            db.close();
        }

        return result;
    }

    public void addCart(String username, String product, float price, String otype) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("product", product);
        cv.put("price", price);
        cv.put("otype", otype);

        SQLiteDatabase db = getWritableDatabase();
        db.insert("cart", null, cv);
        db.close();
    }

    public int checkCart(String username, String product) {
        int result = 0;
        String str[] = new String[2];
        str[0] = username;
        str[1] = product;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " +  TABLE_CART + " WHERE "+ COLUMN_USERNAME+ " =? AND "+COLUMN_PRODUCT +"=?", str);

        if (c.moveToFirst()) {
            result = 1; // Product is in the cart
        }

        c.close();
        db.close();
        return result;
    }

    public void removeCart(String username, String otype) {
        String str[] = new String[2];
        str[0] = username;
        str[1] = otype;

        SQLiteDatabase db = getWritableDatabase();
        db.delete("cart", "username=? AND otype=?", str);
        db.close();
    }

    public ArrayList<String> getCartData(String username, String otype) {
        ArrayList<String> cartData = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        try {
            String[] selectionArgs = {username, otype};
            cursor = db.rawQuery("SELECT * FROM " + TABLE_CART + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_OTYPE + "=?", selectionArgs);

            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String product = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT));
                    @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
                    cartData.add(product + "$" + price);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return cartData;
    }
    public ArrayList<String> getOrderData(String username) {
        ArrayList<String> arr = new ArrayList<>();
        String[] selectionArgs = {username};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor c = db.rawQuery("SELECT * FROM " + TABLE_ORDER_PLACE +" WHERE "+ COLUMN_USERNAME+" = ?", selectionArgs)) {

            while (c.moveToNext()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i <= 8; i++) {
                    if (i > 1) {
                        sb.append("$");
                    }
                    sb.append(c.getString(i));
                }
                arr.add(sb.toString());
            }
        }

        return arr;
    }

    public void addOrder(String username, String fullname, String address, String contactno, int pincode, String date, String time, float price, String otype) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_FULLNAME, fullname);
        contentValues.put(COLUMN_ADDRESS, address);
        contentValues.put(COLUMN_CONTACTNO, contactno);
        contentValues.put(COLUMN_PINCODE, pincode);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_TIME, time); // Note: TIME column should be added to ContentValues
        contentValues.put(COLUMN_PRICE, price);
        contentValues.put(COLUMN_OTYPE, otype);
    }
}