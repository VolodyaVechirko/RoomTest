package com.vvechirko.roomtest.room;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Transaction;

import com.vvechirko.roomtest.room.entity.CommentEntity;
import com.vvechirko.roomtest.room.entity.PostEntity;
import com.vvechirko.roomtest.room.entity.UserEntity;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public abstract class PostDao {

    @Transaction
    public void insert(List<PostEntity> list) {
        insertPosts(list);

        for (PostEntity post : list) {

            if (post.getComments() != null) {
                insertComments(post.getComments());
            }
        }
    }

    @Transaction
    public Single<List<PostEntity>> getPosts() {
        return getPostsAndComments().toObservable()
                .flatMap(Observable::fromIterable)
                .map(row -> convert(row))
                .toList();
    }

    private PostEntity convert(PostSelector row) {
        row.post.setComments(row.comments);
        return row.post;
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertPosts(List<PostEntity> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertComments(List<CommentEntity> list);

    @Query("SELECT * FROM posts")
    public abstract Maybe<List<PostEntity>> getAllPosts();

    @Query("SELECT * FROM posts")
    public abstract Maybe<List<PostSelector>> getPostsAndComments();

    @Query("SELECT * FROM comments")
    public abstract Maybe<List<CommentEntity>> getAllComments();

    @RawQuery
    public abstract Maybe<List<PostEntity>> rawQuery(SupportSQLiteQuery query);

    @Delete
    public abstract void deleteAll(List<PostEntity> list);

    @Query("DELETE FROM posts")
    public abstract void deleteAll();
}
