package com.vvechirko.roomtest.room;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.vvechirko.roomtest.room.entity.CommentEntity;
import com.vvechirko.roomtest.room.entity.PostEntity;

import java.util.List;

public class PostSelector {

    @Embedded
    public PostEntity post;

    @Relation(parentColumn = "id", entityColumn = "postId", entity = CommentEntity.class)
    public List<CommentEntity> comments;
}
