package com.vvechirko.roomtest.realm;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class UserModel implements RealmModel {

    @Ignore
    public static String ID = "id";

    @PrimaryKey
    private int id;
    private String name;

    private AddressModel address;

    private RealmList<PetModel> pets;

    public UserModel() {

    }

    public UserModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public RealmList<PetModel> getPets() {
        return pets;
    }

    public void setPets(RealmList<PetModel> pets) {
        this.pets = pets;
    }
}
