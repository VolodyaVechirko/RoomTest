package com.vvechirko.roomtest.realm;

import io.realm.RealmModel;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class AddressModel implements RealmModel {

    @Ignore
    public static String USER_ID = "userId";

    @PrimaryKey
    private int id;
    @Index
    private int userId;
    private String city;

    public AddressModel() {

    }

    public AddressModel(int id, int userId, String city) {
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
