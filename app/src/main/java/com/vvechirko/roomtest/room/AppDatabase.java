package com.vvechirko.roomtest.room;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.vvechirko.roomtest.room.entity.AddressEntity;
import com.vvechirko.roomtest.room.entity.CommentEntity;
import com.vvechirko.roomtest.room.entity.PetEntity;
import com.vvechirko.roomtest.room.entity.PostEntity;
import com.vvechirko.roomtest.room.entity.UserEntity;

import java.util.Map;

@Database(
        entities = {
                UserEntity.class,
                PetEntity.class,
                AddressEntity.class,
                PostEntity.class,
                CommentEntity.class},
        version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "sample-db";

    public abstract TestDao testDao();

    public abstract UserDao userDao();

    public abstract PostDao postDao();

    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    // util method for converting PARAMS MAP to sql QUERY with WHERE keyword
    public static SimpleSQLiteQuery sqlWhere(String table, Map<String, String> params) {
        String query = "SELECT * FROM " + table;
        String[] keys = params.keySet().toArray(new String[params.size()]);

        for (int i = 0; i < keys.length; i++) {
            query += (i == 0 ? " WHERE" : " AND");
            query += " " + keys[i] + " = ?";
        }

        Object[] args = params.values().toArray();
        return new SimpleSQLiteQuery(query, args);
    }

    private static AppDatabase buildDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME).build();
    }
}
