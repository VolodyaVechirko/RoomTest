package com.vvechirko.roomtest.repository;

import com.vvechirko.roomtest.BasicApp;
import com.vvechirko.roomtest.room.AppDatabase;
import com.vvechirko.roomtest.room.PostDao;
import com.vvechirko.roomtest.room.UserDao;
import com.vvechirko.roomtest.room.entity.PostEntity;
import com.vvechirko.roomtest.room.entity.UserEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

import static com.vvechirko.roomtest.room.AppDatabase.sqlWhere;

public class DbData {

    AppDatabase appDatabase = BasicApp.getInstance().getDatabase();

    UserDb userDb = new UserDb(appDatabase.userDao());
    PostDb postDb = new PostDb(appDatabase.postDao());

    <T> DataSource<T> of(Class<T> clazz) {
        if (clazz.equals(UserEntity.class)) {
            return (DataSource<T>) userDb;
        } else if (clazz.equals(PostEntity.class)) {
            return (DataSource<T>) postDb;
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }

    void clearDb() {
        appDatabase.clearAllTables();
    }

    public class UserDb implements DataSource<UserEntity> {

        final String TABLE_NAME = "users";
        UserDao dao;

        public UserDb(UserDao dao) {
            this.dao = dao;
        }

        @Override
        public Observable<List<UserEntity>> getAll() {
            return dao.getUsers().toObservable();
        }

        @Override
        public Observable<List<UserEntity>> getAll(Query<UserEntity> query) {
            return dao.query(sqlWhere(TABLE_NAME, query.params)).toObservable();
        }

        @Override
        public Completable saveAll(List<UserEntity> list) {
            return Completable.fromAction(() -> dao.insertUsers(list));
        }

        @Override
        public Completable removeAll(List<UserEntity> list) {
            return Completable.fromAction(() -> dao.deleteUsers(list));
        }

        @Override
        public Completable removeAll() {
            return Completable.fromAction(() -> dao.deleteUsers());
        }
    }

    public class PostDb implements DataSource<PostEntity> {

        final String TABLE_NAME = "posts";
        PostDao dao;

        public PostDb(PostDao dao) {
            this.dao = dao;
        }

        @Override
        public Observable<List<PostEntity>> getAll() {
            return dao.getAll().toObservable();
        }

        @Override
        public Observable<List<PostEntity>> getAll(Query<PostEntity> query) {
            return dao.rawQuery(sqlWhere(TABLE_NAME, query.params)).toObservable();
        }

        @Override
        public Completable saveAll(List<PostEntity> list) {
            return Completable.fromAction(() -> dao.insert(list));
        }

        @Override
        public Completable removeAll(List<PostEntity> list) {
            return Completable.fromAction(() -> dao.deleteAll(list));
        }

        @Override
        public Completable removeAll() {
            return Completable.fromAction(() -> dao.deleteAll());
        }
    }
}
