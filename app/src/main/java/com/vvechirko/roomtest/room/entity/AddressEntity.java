package com.vvechirko.roomtest.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "address",
        indices = {@Index(value = "userId")},
        foreignKeys = {
                @ForeignKey(entity = UserEntity.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE)
        })
public class AddressEntity {

    @PrimaryKey
    private int id;
    private int userId;
    private String city;

    // some other fields


    public AddressEntity() {

    }

    public AddressEntity(int id, int userId, String city) {
        this.id = id;
        this.userId = userId;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
