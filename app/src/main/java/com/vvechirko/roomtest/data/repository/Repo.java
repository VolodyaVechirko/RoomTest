package com.vvechirko.roomtest.data.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;

public interface Repo {

    <T> Flowable<List<T>> getAll(Class<T> clazz);

    <T> Flowable<T> get(Class<T> clazz, String id);

    <T> Flowable<List<T>> query(Query query);

    <T> Flowable<List<T>> saveAll(Class<T> clazz, List<T> list);

    <T> Flowable<T> save(Class<T> clazz, T item);

    <T> Flowable<List<T>> removeAll(Class<T> clazz, List<T> list);

    <T> Flowable<T> remove(Class<T> clazz, T item);

    <T> Flowable<List<T>> removeAll(Class<T> clazz);


    class Query {

        Repo repo;
        Map<String, String> params = new HashMap<>();
        Class clazz;

        public Query(Repo repo, Class clazz) {
            this.repo = repo;
            this.clazz = clazz;
        }

        public Query where(String property, String value) {
            params.put(property, value);
            return this;
        }

        public <T> Flowable<List<T>> query() {
            return repo.query(this);
        }
    }
}
