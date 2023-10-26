package com.example.medproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE medicamentos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, dosis INTEGER NOT NULL CHECK (dosis > 0),intervalo INTEGER NOT NULL, fecha_inicio TEXT NOT NULL, hora_inicio TEXT NOT NULL, fecha_fin TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE medicamentos");
        onCreate(db);
    }
}
