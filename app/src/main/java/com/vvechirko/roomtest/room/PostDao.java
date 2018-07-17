package com.vvechirko.roomtest.room;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;

import com.vvechirko.roomtest.room.entity.PostEntity;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public abstract class PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<PostEntity> list);

    @Query("SELECT * FROM posts")
    public abstract Maybe<List<PostEntity>> getAll();

    @RawQuery
    public abstract Maybe<List<PostEntity>> rawQuery(SupportSQLiteQuery query);

    @Delete
    public abstract void deleteAll(List<PostEntity> list);

    @Query("DELETE FROM posts")
    public abstract void deleteAll();
}
