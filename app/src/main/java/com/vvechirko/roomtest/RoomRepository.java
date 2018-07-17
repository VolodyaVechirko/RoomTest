package com.vvechirko.roomtest;

import com.vvechirko.roomtest.room.AppDatabase;
import com.vvechirko.roomtest.room.entity.UserEntity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class RoomRepository {

    AppDatabase appDatabase;
    ApiData apiData;

    public RoomRepository(AppDatabase db) {
        appDatabase = db;
        apiData = new ApiData();
    }

    public Flowable<List<UserEntity>> getUsersFlowable() {
        return Flowable.merge(
                appDatabase.testDao().getUsersFlowable()
                        .observeOn(Schedulers.io()),
                Flowable.defer(() -> isNetworkAvailable()
                        ? apiData.getUsersFlowable()
                        .observeOn(Schedulers.io())
                        .doOnNext(l -> appDatabase.testDao().insertUsers(l))
                        : Flowable.empty()
                )
        );
    }

    public Flowable<List<UserEntity>> getUsersSingle() {
        return Single.merge(
                appDatabase.testDao().getUsersSingle()
                        .observeOn(Schedulers.io()),
                Single.defer(() -> isNetworkAvailable()
                        ? apiData.getUsersSingle()
                        .observeOn(Schedulers.io())
                        .doOnSuccess(l -> appDatabase.testDao().insertUsers(l))
                        : Single.error(new IllegalArgumentException("no internet"))
                )
        );
    }

    public Flowable<List<UserEntity>> getUsersMaybe() {
        return Maybe.merge(
                appDatabase.testDao().getUsersMaybe()
                        .observeOn(Schedulers.io()),
                Maybe.defer(() -> isNetworkAvailable()
                        ? apiData.getUsersSingle().toMaybe()
                        .observeOn(Schedulers.io())
                        .doOnSuccess(l -> appDatabase.testDao().insertUsers(l))
                        : Maybe.empty()
                )
        );
    }

    boolean isNetworkAvailable() {
        return true;
    }
}
