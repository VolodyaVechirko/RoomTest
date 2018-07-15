package com.vvechirko.roomtest.data;

import com.vvechirko.roomtest.room.entity.PostEntity;
import com.vvechirko.roomtest.room.entity.UserEntity;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;

public class ApiData {

    UserApi userApi = new UserApi();
    PostApi postApi = new PostApi();

    public Api of(Class clazz) {
        if (clazz.equals(UserEntity.class)) {
            return userApi;
        } else if (clazz.equals(PostEntity.class)) {
            return postApi;
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }

    public interface Api<T> {
        Flowable<List<T>> getAll();

        Flowable<T> get(String id);

        Flowable<List<T>> saveAll(List<T> list);

        Flowable<T> save(T item);

        Flowable<List<T>> removeAll(List<T> list);

        Flowable<T> remove(T item);

        Flowable<List<T>> removeAll();
    }

    class UserApi implements Api<UserEntity> {

        @Override
        public Flowable<List<UserEntity>> getAll() {
            return simulateRequest(Arrays.asList(new UserEntity(), new UserEntity()));
        }

        @Override
        public Flowable<UserEntity> get(String id) {
            return simulateRequest(new UserEntity());
        }

        @Override
        public Flowable<List<UserEntity>> saveAll(List<UserEntity> list) {
            return simulateRequest(Arrays.asList(new UserEntity(), new UserEntity()));
        }

        @Override
        public Flowable<UserEntity> save(UserEntity item) {
            return simulateRequest(new UserEntity());
        }

        @Override
        public Flowable<List<UserEntity>> removeAll(List<UserEntity> list) {
            return simulateRequest(Arrays.asList(new UserEntity(), new UserEntity()));
        }

        @Override
        public Flowable<UserEntity> remove(UserEntity item) {
            return simulateRequest(new UserEntity());
        }

        @Override
        public Flowable<List<UserEntity>> removeAll() {
            return simulateRequest(Arrays.asList(new UserEntity(), new UserEntity()));
        }
    }

    class PostApi implements Api<PostEntity> {

        @Override
        public Flowable<List<PostEntity>> getAll() {
            return simulateRequest(Arrays.asList(new PostEntity(), new PostEntity()));
        }

        @Override
        public Flowable<PostEntity> get(String id) {
            return simulateRequest(new PostEntity());
        }

        @Override
        public Flowable<List<PostEntity>> saveAll(List<PostEntity> list) {
            return simulateRequest(Arrays.asList(new PostEntity(), new PostEntity()));
        }

        @Override
        public Flowable<PostEntity> save(PostEntity item) {
            return simulateRequest(new PostEntity());
        }

        @Override
        public Flowable<List<PostEntity>> removeAll(List<PostEntity> list) {
            return simulateRequest(Arrays.asList(new PostEntity(), new PostEntity()));
        }

        @Override
        public Flowable<PostEntity> remove(PostEntity item) {
            return simulateRequest(new PostEntity());
        }

        @Override
        public Flowable<List<PostEntity>> removeAll() {
            return simulateRequest(Arrays.asList(new PostEntity(), new PostEntity()));
        }
    }

    public <T> Flowable<T> simulateRequest(T data) {
        return Flowable.timer(2000, TimeUnit.MILLISECONDS)
                .flatMap(o -> Flowable.just(data));
    }
}
