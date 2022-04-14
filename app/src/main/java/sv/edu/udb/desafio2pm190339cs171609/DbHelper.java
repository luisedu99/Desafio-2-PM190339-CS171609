package sv.edu.udb.desafio2pm190339cs171609;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pharmacy.db";
    public static final String TABLE_MEDICINES = "t_medicines";
    public static final String TABLE_DETAIL = "t_detail";
    public static final String TABLE_BILLS = "t_bills";


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_MEDICINES + "(" +
                "id_medicine INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "price REAL NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLE_BILLS + "(" +
                "id_bill INTEGER PRIMARY KEY AUTOINCREMENT," +
                "client TEXT NOT NULL," +
                "bill_date TEXT NOT NULL," +
                "total_amount REAL," +
                "status_bill INTEGER )");

        db.execSQL("CREATE TABLE " + TABLE_DETAIL + "(" +
                "id_detail INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_billFk INTEGER NOT NULL," +
                "id_medicineFk INTEGER NOT NULL," +
                "quantity INTEGER NOT NULL," +
                "price REAL NOT NULL," +
                "total REAL NOT NULL," +
                "FOREIGN KEY(id_billFk) REFERENCES t_bills(id_bill)," +
                "FOREIGN KEY(id_medicineFk) REFERENCES t_medicines(id_medicine))");

        chargeMedicinesData(db);

    }

    private void chargeMedicinesData(SQLiteDatabase db){
        db.execSQL("INSERT INTO " + TABLE_MEDICINES + " VALUES(1,'Acetaminofen',2.50)");
        db.execSQL("INSERT INTO " + TABLE_MEDICINES + " VALUES(2,'Amoxicilina',1.25)");
        db.execSQL("INSERT INTO " + TABLE_MEDICINES + " VALUES(3,'Loratadina',0.50)");
        db.execSQL("INSERT INTO " + TABLE_MEDICINES + " VALUES(4,'Prednisona',0.75)");
        db.execSQL("INSERT INTO " + TABLE_MEDICINES + " VALUES(5,'Tabcin tos',0.50)");
        db.execSQL("INSERT INTO " + TABLE_MEDICINES + " VALUES(6,'Panadol ultra',0.25)");
        db.execSQL("INSERT INTO " + TABLE_MEDICINES + " VALUES(7,'Ibuprofeno',0.65)");
        db.execSQL("INSERT INTO " + TABLE_MEDICINES + " VALUES(8,'Baytalcid',0.30)");
        db.execSQL("INSERT INTO " + TABLE_MEDICINES + " VALUES(9,'Azitromicina',1.35)");
        db.execSQL("INSERT INTO " + TABLE_MEDICINES + " VALUES(10,'Alka seltzer',0.50)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE " + TABLE_MEDICINES);
        db.execSQL("DROP TABLE " + TABLE_BILLS);
        db.execSQL("DROP TABLE " + TABLE_DETAIL);
        onCreate(db);
    }
}
