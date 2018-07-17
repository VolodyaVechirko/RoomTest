package com.vvechirko.roomtest.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;

interface DataSource<T> {

    Observable<List<T>> getAll();

    Observable<List<T>> getAll(Query<T> query);

    Completable saveAll(List<T> list);

    Completable removeAll(List<T> list);

    Completable removeAll();

    default Query<T> query() {
        return new Query<T>(this);
    }

    class Query<T> {

        DataSource<T> dataSource;
        Map<String, String> params = new HashMap<>();

        public Query(DataSource<T> source) {
            this.dataSource = source;
        }

        public boolean has(String property) {
            return params.get(property) != null;
        }

        public String get(String property) {
            return params.get(property);
        }

        public Query<T> where(String property, String value) {
            params.put(property, value);
            return this;
        }

        public Observable<List<T>> findAll() {
            return dataSource.getAll(this);
        }

        public Observable<T> findFirst() {
            return dataSource.getAll(this)
                    .map(l -> l.size() > 0 ? l.get(0) : null);
        }
    }
}
