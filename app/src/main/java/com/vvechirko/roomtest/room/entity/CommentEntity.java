package com.vvechirko.roomtest.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "comments",
        indices = {@Index(value = "postId")},
        foreignKeys = {
                @ForeignKey(entity = PostEntity.class,
                        parentColumns = "id",
                        childColumns = "postId",
                        onDelete = ForeignKey.CASCADE)
        })
public class CommentEntity {

    @PrimaryKey
    private int id;
    private int postId;
    private String comment;

    public CommentEntity() {
    }

    public CommentEntity(int id, int postId, String comment) {
        this.id = id;
        this.postId = postId;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
