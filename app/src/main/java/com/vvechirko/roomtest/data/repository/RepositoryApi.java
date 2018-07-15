package com.vvechirko.roomtest.data.repository;

import com.vvechirko.roomtest.data.ApiData;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("unchecked")
public class RepositoryApi implements Repo {

    ApiData apiData = new ApiData();

    @Override
    public <T> Flowable<List<T>> getAll(Class<T> clazz) {
        return apiData.of(clazz).getAll()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public <T> Flowable<T> get(Class<T> clazz, String id) {
        return apiData.of(clazz).get(id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public <T> Flowable<List<T>> query(Query query) {
        throw new IllegalArgumentException("Not implemented");
    }

    @Override
    public <T> Flowable<List<T>> saveAll(Class<T> clazz, List<T> list) {
        return apiData.of(clazz).saveAll(list)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public <T> Flowable<T> save(Class<T> clazz, T item) {
        return apiData.of(clazz).save(item)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public <T> Flowable<List<T>> removeAll(Class<T> clazz, List<T> list) {
        return apiData.of(clazz).removeAll(list)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public <T> Flowable<T> remove(Class<T> clazz, T item) {
        return apiData.of(clazz).remove(item)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public <T> Flowable<List<T>> removeAll(Class<T> clazz) {
        return apiData.of(clazz).removeAll()
                .subscribeOn(Schedulers.io());
    }
}
