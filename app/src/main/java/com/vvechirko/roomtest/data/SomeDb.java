package com.vvechirko.roomtest.data;

import com.vvechirko.roomtest.room.entity.PostEntity;
import com.vvechirko.roomtest.room.entity.UserEntity;

import java.util.List;

import io.reactivex.Flowable;

public class SomeDb {

    UserDao userDao = new UserDao();
    PostDao postDao = new PostDao();

    public Dao daoOf(Class clazz) {
        if (clazz.equals(UserEntity.class)) {
            return userDao;
        } else if (clazz.equals(PostEntity.class)) {
            return postDao;
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }

    public interface Dao<T> {
        Flowable<List<T>> getAll();

        Flowable<T> get(String id);

        Flowable<List<T>> saveAll(List<T> list);

        Flowable<T> save(T item);

        Flowable<List<T>> removeAll(List<T> list);

        Flowable<T> remove(T item);

        Flowable<List<T>> removeAll();
    }

    class UserDao implements Dao<UserEntity> {

        @Override
        public Flowable<List<UserEntity>> getAll() {
            return null;
        }

        @Override
        public Flowable<UserEntity> get(String id) {
            return null;
        }

        @Override
        public Flowable<List<UserEntity>> saveAll(List<UserEntity> list) {
            return null;
        }

        @Override
        public Flowable<UserEntity> save(UserEntity item) {
            return null;
        }

        @Override
        public Flowable<List<UserEntity>> removeAll(List<UserEntity> list) {
            return null;
        }

        @Override
        public Flowable<UserEntity> remove(UserEntity item) {
            return null;
        }

        @Override
        public Flowable<List<UserEntity>> removeAll() {
            return null;
        }
    }

    class PostDao implements Dao<PostEntity> {

        @Override
        public Flowable<List<PostEntity>> getAll() {
            return null;
        }

        @Override
        public Flowable<PostEntity> get(String id) {
            return null;
        }

        @Override
        public Flowable<List<PostEntity>> saveAll(List<PostEntity> list) {
            return null;
        }

        @Override
        public Flowable<PostEntity> save(PostEntity item) {
            return null;
        }

        @Override
        public Flowable<List<PostEntity>> removeAll(List<PostEntity> list) {
            return null;
        }

        @Override
        public Flowable<PostEntity> remove(PostEntity item) {
            return null;
        }

        @Override
        public Flowable<List<PostEntity>> removeAll() {
            return null;
        }
    }
}
