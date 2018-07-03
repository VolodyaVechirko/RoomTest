package com.vvechirko.roomtest.db.entity;

import com.vvechirko.roomtest.model.Comment;

import java.util.Date;

public class CommentModel implements Comment {

    private int id;
    private int productId;
    private String text;
    private Date postedAt;

    public CommentModel() {

    }

    public CommentModel(int id, int productId, String text, Date postedAt) {
        this.id = id;
        this.productId = productId;
        this.text = text;
        this.postedAt = postedAt;
    }

    public CommentModel(Comment comment) {
        this.id = comment.getId();
        this.productId = comment.getProductId();
        this.text = comment.getText();
        this.postedAt = comment.getPostedAt();
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Date getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
    }

}
