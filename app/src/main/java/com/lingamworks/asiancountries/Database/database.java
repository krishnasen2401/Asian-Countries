package com.lingamworks.asiancountries.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.lingamworks.asiancountries.Models.Country;
import com.lingamworks.asiancountries.Models.Language;


@Database(entities = {Country.class, Language.class},version = 1,exportSchema = false)
@TypeConverters(typeconverters.class)
public abstract class database extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "countries";
    private static database sInstance;

    public static database getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        database.class, database.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }
    public abstract CountriesDao CountriesDato();
}
