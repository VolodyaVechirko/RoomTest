package com.vvechirko.roomtest.data;

import com.vvechirko.roomtest.room.entity.PostEntity;
import com.vvechirko.roomtest.room.entity.UserEntity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class RepositoryTyped {

    Repo<UserEntity> userRepo = new Repo<>(UserEntity.class);
    Repo<PostEntity> postRepo = new Repo<>(PostEntity.class);

//    Repo<PostEntity> postRepo = new Repo<>(new ApiData().postApi, new SomeDb().postDao);

    class Repo<T> {

        ApiData.Api<T> api;
        SomeDb.Dao<T> dao;

        Repo(Class<T> clazz) {
            api = new ApiData().of(clazz);
            dao = new SomeDb().daoOf(clazz);
        }

//        Repo(ApiData.Api<T> api, SomeDb.Dao<T> dao) {
//            this.api = api;
//            this.dao = dao;
//        }

        public Flowable<List<T>> getAll() {
            return Flowable.merge(

                    dao.getAll()
                            .subscribeOn(Schedulers.io()),

                    Flowable.defer(() -> isNetworkAvailable()

                            ? api.getAll()
                            .subscribeOn(Schedulers.io())
                            .flatMap(l -> dao.saveAll(l))

                            : Flowable.empty()
                    )
            );
        }

        public Flowable<T> get(String id) {
            return Flowable.merge(

                    dao.get(id)
                            .subscribeOn(Schedulers.io()),

                    Flowable.defer(() -> isNetworkAvailable()

                            ? api.get(id)
                            .subscribeOn(Schedulers.io())
                            .flatMap(l -> dao.save(l))

                            : Flowable.empty()
                    )
            );
        }

        public Flowable<List<T>> saveAll(List<T> list) {
            return Flowable.defer(() -> isNetworkAvailable()

                    ? api.saveAll(list)
                    .subscribeOn(Schedulers.io())
                    .flatMap(l -> dao.saveAll(l))

                    : Flowable.error(new IllegalAccessError("You are offline"))
            );
        }

        public Flowable<T> save(T item) {
            return Flowable.defer(() -> isNetworkAvailable()

                    ? api.save(item)
                    .subscribeOn(Schedulers.io())
                    .flatMap(l -> dao.save(l))

                    : Flowable.error(new IllegalAccessError("You are offline"))
            );
        }

        public Flowable<List<T>> removeAll(List<T> list) {
            return Flowable.defer(() -> isNetworkAvailable()

                    ? api.removeAll(list)
                    .subscribeOn(Schedulers.io())
                    .flatMap(l -> dao.removeAll(l))

                    : Flowable.error(new IllegalAccessError("You are offline"))
            );
        }

        public Flowable<T> remove(T item) {
            return Flowable.defer(() -> isNetworkAvailable()

                    ? api.remove(item)
                    .subscribeOn(Schedulers.io())
                    .flatMap(l -> dao.remove(l))

                    : Flowable.error(new IllegalAccessError("You are offline"))
            );
        }

        public Flowable<List<T>> removeAll() {
            return Flowable.defer(() -> isNetworkAvailable()

                    ? api.removeAll()
                    .subscribeOn(Schedulers.io())
                    .flatMap(l -> dao.removeAll())

                    : Flowable.error(new IllegalAccessError("You are offline"))
            );
        }

        boolean isNetworkAvailable() {
            return true;
        }
    }
}
