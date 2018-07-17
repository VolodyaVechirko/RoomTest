package com.vvechirko.roomtest.room;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Transaction;

import com.vvechirko.roomtest.room.entity.AddressEntity;
import com.vvechirko.roomtest.room.entity.PetEntity;
import com.vvechirko.roomtest.room.entity.UserEntity;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public abstract class UserDao {

    @Transaction
    public void insertUsers(List<UserEntity> users) {
        _insertUsers(users);

        for (UserEntity user : users) {

            if (user.getAddress() != null) {
                _insertAddress(user.getAddress());
            }

            if (user.getPets() != null) {
                _insertPets(user.getPets());
            }
        }
    }

    @Transaction
    public Single<List<UserEntity>> getUsers() {
        return _loadUsers().toObservable()
                .flatMap(Observable::fromIterable)
                .map(row -> convert(row))
                .toList();
    }

    @Transaction
    public Single<List<UserEntity>> query(SupportSQLiteQuery query) {
        return _rawQuery(query).toObservable()
                .flatMap(Observable::fromIterable)
                .map(row -> convert(row))
                .toList();
    }

    private UserEntity convert(UserSelector row) {
        row.user.setPets(row.pets);
        if (!row.address.isEmpty()) {
            row.user.setAddress(row.address.get(0));
        }
        return row.user;
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void _insertPets(List<PetEntity> pets);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void _insertUsers(List<UserEntity> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void _insertAddress(AddressEntity address);

    @Query("SELECT * FROM users")
    abstract Maybe<List<UserSelector>> _loadUsers();

    @RawQuery
    public abstract Maybe<List<UserSelector>> _rawQuery(SupportSQLiteQuery query);

    @Delete
    public abstract void deleteUsers(List<UserEntity> users);

    @Query("DELETE FROM users")
    public abstract void deleteUsers();
}
