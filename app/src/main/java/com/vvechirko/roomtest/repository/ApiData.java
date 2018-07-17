package com.vvechirko.roomtest.repository;

import com.vvechirko.roomtest.room.entity.PostEntity;
import com.vvechirko.roomtest.room.entity.UserEntity;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class ApiData {

    static <T> Observable<T> simulateRequest(T data) {
        return Observable.timer(2000, TimeUnit.MILLISECONDS)
                .flatMap(o -> Observable.just(data));
    }

    static Completable simulateRequest() {
        return Completable.timer(2000, TimeUnit.MILLISECONDS);
    }

    UserApi userApi = new UserApi();
    PostApi postApi = new PostApi();

    <T> DataSource<T> of(Class<T> clazz) {
        if (clazz.equals(UserEntity.class)) {
            return (DataSource<T>) userApi;
        } else if (clazz.equals(PostEntity.class)) {
            return (DataSource<T>) postApi;
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }

    public class UserApi implements DataSource<UserEntity> {

        @Override
        public Observable<List<UserEntity>> getAll() {
            return simulateRequest(Arrays.asList());
        }

        @Override
        public Observable<List<UserEntity>> getAll(Query<UserEntity> query) {
            return simulateRequest(Arrays.asList());
        }

        @Override
        public Completable saveAll(List<UserEntity> list) {
            return simulateRequest();
        }

        @Override
        public Completable removeAll(List<UserEntity> list) {
            return simulateRequest();
        }

        @Override
        public Completable removeAll() {
            return simulateRequest();
        }
    }

    public class PostApi implements DataSource<PostEntity> {

        @Override
        public Observable<List<PostEntity>> getAll() {
            return simulateRequest(Arrays.asList());
        }

        @Override
        public Observable<List<PostEntity>> getAll(Query<PostEntity> query) {
            return simulateRequest(Arrays.asList());
        }

        @Override
        public Completable saveAll(List<PostEntity> list) {
            return simulateRequest();
        }

        @Override
        public Completable removeAll(List<PostEntity> list) {
            return simulateRequest();
        }

        @Override
        public Completable removeAll() {
            return simulateRequest();
        }
    }
}

