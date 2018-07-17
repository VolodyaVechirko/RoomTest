package com.vvechirko.roomtest.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.vvechirko.roomtest.room.entity.AddressEntity;
import com.vvechirko.roomtest.room.entity.PetEntity;
import com.vvechirko.roomtest.room.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public abstract class TestDao {

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
    public List<UserEntity> getUsers() {
        List<UserSelector> usersWithPets = _loadUsers();
        List<UserEntity> users = new ArrayList<>();

        for (UserSelector row : usersWithPets) {
            users.add(convert(row));
        }
        return users;
    }

    @Transaction
    public Flowable<List<UserEntity>> getUsersFlowable() {
        return _loadUsersFlowable()
                .flatMap(Flowable::fromIterable)
                .map(row -> convert(row))
                .toList().toFlowable();
    }

    @Transaction
    public Single<List<UserEntity>> getUsersSingle() {
        return _loadUsersSingle().toObservable()
                .flatMap(Observable::fromIterable)
                .map(row -> convert(row))
                .toList();
    }

    @Transaction
    public Maybe<List<UserEntity>> getUsersMaybe() {
        return _loadUsersMaybe().toObservable()
                .flatMap(Observable::fromIterable)
                .map(row -> convert(row))
                .toList().toMaybe();
    }

    @Transaction
    public UserEntity getUser(int userId) {
        UserSelector row = _loadUser(userId);
        return convert(row);
    }

    private UserEntity convert(UserSelector row) {
        row.user.setPets(row.pets);
        if (!row.address.isEmpty()) {
            row.user.setAddress(row.address.get(0));
        }
        return row.user;
    }

    public void deleteUsers(List<UserEntity> users) {
        _deleteUsers(users);
    }


    //package private methods so that wrapper methods are used, Room allows for this,
    // but not private methods, hence the underscores to put people off using them :)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void _insertPets(List<PetEntity> pets);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void _insertUsers(List<UserEntity> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void _insertAddress(AddressEntity address);

    @Query("SELECT * FROM users")
    abstract List<UserSelector> _loadUsers();

    @Query("SELECT * FROM users")
    abstract Flowable<List<UserSelector>> _loadUsersFlowable();

    @Query("SELECT * FROM users")
    abstract Single<List<UserSelector>> _loadUsersSingle();

    @Query("SELECT * FROM users")
    abstract Maybe<List<UserSelector>> _loadUsersMaybe();

    @Delete
    abstract void _deleteUsers(List<UserEntity> users);


    @Query("SELECT * FROM users where id = :userId")
    abstract UserSelector _loadUser(int userId);

    @Query("SELECT * FROM pets where userId = :userId")
    abstract List<PetEntity> _loadPets(int userId);

    @Query("SELECT * FROM pets")
    public abstract List<PetEntity> loadPets();

    @Query("SELECT * FROM address")
    public abstract List<AddressEntity> loadAddress();
}
