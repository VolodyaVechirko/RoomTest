package com.vvechirko.roomtest.data.repository;

import com.vvechirko.roomtest.data.SomeDb;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("unchecked")
public class RepositoryDb implements Repo {

    //    AppDatabase db = BasicApp.getInstance().getDatabase();
    SomeDb someDb = new SomeDb();

    @Override
    public <T> Flowable<List<T>> getAll(Class<T> clazz) {
        return someDb.daoOf(clazz).getAll()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public <T> Flowable<T> get(Class<T> clazz, String id) {
        return someDb.daoOf(clazz).get(id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public <T> Flowable<List<T>> query(Query query) {
        throw new IllegalArgumentException("Not implemented");
    }

    @Override
    public <T> Flowable<List<T>> saveAll(Class<T> clazz, List<T> list) {
        return someDb.daoOf(clazz).saveAll(list)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public <T> Flowable<T> save(Class<T> clazz, T item) {
        return someDb.daoOf(clazz).save(item)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public <T> Flowable<List<T>> removeAll(Class<T> clazz, List<T> list) {
        return someDb.daoOf(clazz).removeAll(list)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public <T> Flowable<T> remove(Class<T> clazz, T item) {
        return someDb.daoOf(clazz).remove(item)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public <T> Flowable<List<T>> removeAll(Class<T> clazz) {
        return someDb.daoOf(clazz).removeAll()
                .subscribeOn(Schedulers.io());
    }
}
