package com.vvechirko.roomtest.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.vvechirko.roomtest.db.entity.CommentEntity;

import java.util.List;

@Dao
public interface CommentDao {

    @Query("SELECT * FROM comments where productId = :productId")
    List<CommentEntity> loadComments(int productId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveComments(List<CommentEntity> comments);
}
