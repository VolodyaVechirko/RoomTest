package com.vvechirko.roomtest.db.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class ProductWithComments {

    @Embedded
    private ProductEntity product;

    @Relation(parentColumn = "id", entityColumn = "productId", entity = CommentEntity.class)
    private List<CommentEntity> comments;

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }
}
