package com.vvechirko.roomtest.db.pets;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class UserSelector {

    @Embedded
    public UserEntity user;

    @Relation(parentColumn = "id", entityColumn = "userId", entity = PetEntity.class)
    public List<PetEntity> pets;

    @Relation(parentColumn = "id", entityColumn = "userId", entity = AddressEntity.class)
    public List<AddressEntity> address;
}
