package com.vvechirko.roomtest.repository;

import com.vvechirko.roomtest.room.entity.PostEntity;
import com.vvechirko.roomtest.room.entity.UserEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static com.vvechirko.roomtest.BasicApp.isNetworkAvailable;

public class Repository {

    ApiData apiData = new ApiData();
    DbData dbData = new DbData();

    Repo<UserEntity> userRepo = new Repo<UserEntity>(UserEntity.class);
    Repo<PostEntity> postRepo = new Repo<PostEntity>(PostEntity.class);

    public <T> Repo<T> of(Class<T> clazz) {
        if (clazz.equals(UserEntity.class)) {
            return (Repo<T>) userRepo;
        } else if (clazz.equals(PostEntity.class)) {
            return (Repo<T>) postRepo;
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }

    public void clearDatabase() {
        dbData.clearDb();
    }

    public class Repo<T> implements DataSource<T> {

        DataSource<T> api;
        DataSource<T> dao;

        Repo(Class<T> clazz) {
            api = apiData.of(clazz);
            dao = dbData.of(clazz);
        }

        @Override
        public Observable<List<T>> getAll() {
            return Observable.concatArrayEager(

                    dao.getAll()
                            .subscribeOn(Schedulers.io()),

                    Observable.defer(() -> isNetworkAvailable()

                            ? api.getAll()
                            .subscribeOn(Schedulers.io())
                            .flatMap(l -> dao.saveAll(l).andThen(Observable.just(l)))

                            : Observable.empty()
                    )
            );
        }

        @Override
        public Observable<List<T>> getAll(Query<T> query) {
            return Observable.concatArrayEager(

                    dao.getAll(query)
                            .subscribeOn(Schedulers.io()),

                    Observable.defer(() -> isNetworkAvailable()

                            ? api.getAll(query)
                            .subscribeOn(Schedulers.io())
                            .flatMap(l -> dao.saveAll(l).andThen(Observable.just(l)))

                            : Observable.empty()
                    )
            );
        }

        @Override
        public Completable saveAll(List<T> list) {
            return Completable.defer(() -> isNetworkAvailable()

                    ? api.saveAll(list)
                    .subscribeOn(Schedulers.io())
                    .andThen(dao.saveAll(list))

                    : Completable.error(new IllegalAccessError("You are offline"))
            );
        }

        @Override
        public Completable removeAll(List<T> list) {
            return Completable.defer(() -> isNetworkAvailable()

                    ? api.removeAll(list)
                    .subscribeOn(Schedulers.io())
                    .andThen(dao.removeAll(list))

                    : Completable.error(new IllegalAccessError("You are offline"))
            );
        }

        @Override
        public Completable removeAll() {
            return Completable.defer(() -> isNetworkAvailable()

                    ? api.removeAll()
                    .subscribeOn(Schedulers.io())
                    .andThen(dao.removeAll())

                    : Completable.error(new IllegalAccessError("You are offline"))
            );
        }
    }
}
