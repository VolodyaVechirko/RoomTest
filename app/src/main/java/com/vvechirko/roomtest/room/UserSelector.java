package com.vvechirko.roomtest.room;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.vvechirko.roomtest.room.entity.AddressEntity;
import com.vvechirko.roomtest.room.entity.PetEntity;
import com.vvechirko.roomtest.room.entity.UserEntity;

import java.util.List;

public class UserSelector {

    @Embedded
    public UserEntity user;

    @Relation(parentColumn = "id", entityColumn = "userId", entity = PetEntity.class)
    public List<PetEntity> pets;

    @Relation(parentColumn = "id", entityColumn = "userId", entity = AddressEntity.class)
    public List<AddressEntity> address;
}
