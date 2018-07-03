package com.vvechirko.roomtest.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.vvechirko.roomtest.room.entity.AddressEntity;
import com.vvechirko.roomtest.room.entity.PetEntity;

import java.util.List;

@Entity(tableName = "users")
public class UserEntity {

    @PrimaryKey
    private int id;
    private String name;

    // other fields...
    @Ignore
    private AddressEntity address;

    @Ignore
    private List<PetEntity> pets;

    public UserEntity() {

    }

    public UserEntity(int id, String name) {
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

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public List<PetEntity> getPets() {
        return pets;
    }

    public void setPets(List<PetEntity> pets) {
        this.pets = pets;
    }
}
