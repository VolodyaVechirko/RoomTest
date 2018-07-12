package com.vvechirko.roomtest;

import com.vvechirko.roomtest.room.entity.AddressEntity;
import com.vvechirko.roomtest.room.entity.PetEntity;
import com.vvechirko.roomtest.room.entity.UserEntity;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ApiData {

    public Single<List<UserEntity>> getUsersSingle() {
        return Single.timer(2000, TimeUnit.SECONDS)
                .flatMap(o -> Single.just(Arrays.asList(getUser())));
    }

    public Observable<List<UserEntity>> getUsersObservable() {
        return Observable.timer(2000, TimeUnit.SECONDS)
                .flatMap(o -> Observable.just(Arrays.asList(getUser())));
    }

    public Flowable<List<UserEntity>> getUsersFlowable() {
        return Flowable.timer(2000, TimeUnit.SECONDS)
                .flatMap(o -> Flowable.just(Arrays.asList(getUser())));
    }

    public UserEntity getUser() {
        UserEntity user = new UserEntity(15, "John");
        AddressEntity address = new AddressEntity(20, 15, "London");
        PetEntity pet1 = new PetEntity(2, 15, "Rex");
        PetEntity pet2 = new PetEntity(4, 15, "Bax");
        user.setAddress(address);
        user.setPets(Arrays.asList(pet1, pet2));
        return user;
    }
}
