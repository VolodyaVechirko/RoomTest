package com.vvechirko.roomtest.data.repository;

import java.util.List;

import io.reactivex.Flowable;

@SuppressWarnings("unchecked")
public class Repository implements Repo {

    Repo api = new RepositoryApi();
    Repo db = new RepositoryDb();

    @Override
    public <T> Flowable<List<T>> getAll(Class<T> clazz) {
        return Flowable.merge(db.getAll(clazz),
                Flowable.defer(() -> isNetworkAvailable()
                        ? api.getAll(clazz).flatMap(l -> db.saveAll(clazz, l))
                        : Flowable.empty()
                )
        );
    }

    @Override
    public <T> Flowable<T> get(Class<T> clazz, String id) {
        return Flowable.merge(db.get(clazz, id),
                Flowable.defer(() -> isNetworkAvailable()
                        ? api.get(clazz, id).flatMap(l -> db.save(clazz, l))
                        : Flowable.empty()
                )
        );
    }

    @Override
    public <T> Flowable<List<T>> query(Query query) {
        return Flowable.merge(db.query(query),
                Flowable.defer(() -> isNetworkAvailable()
                        ? api.query(query).flatMap(l -> db.saveAll(query.clazz, l))
                        : Flowable.empty()
                )
        );
    }

    @Override
    public <T> Flowable<List<T>> saveAll(Class<T> clazz, List<T> list) {
        return Flowable.defer(() -> isNetworkAvailable()
                ? api.saveAll(clazz, list).flatMap(l -> db.saveAll(clazz, l))
                : Flowable.error(new IllegalAccessError("You are offline"))
        );
    }

    @Override
    public <T> Flowable<T> save(Class<T> clazz, T item) {
        return Flowable.defer(() -> isNetworkAvailable()
                ? api.save(clazz, item).flatMap(l -> db.save(clazz, l))
                : Flowable.error(new IllegalAccessError("You are offline"))
        );
    }

    @Override
    public <T> Flowable<List<T>> removeAll(Class<T> clazz, List<T> list) {
        return Flowable.defer(() -> isNetworkAvailable()
                ? api.removeAll(clazz, list).flatMap(l -> db.removeAll(clazz, l))
                : Flowable.error(new IllegalAccessError("You are offline"))
        );
    }

    @Override
    public <T> Flowable<T> remove(Class<T> clazz, T item) {
        return Flowable.defer(() -> isNetworkAvailable()
                ? api.remove(clazz, item).flatMap(l -> db.remove(clazz, l))
                : Flowable.error(new IllegalAccessError("You are offline"))
        );
    }

    @Override
    public <T> Flowable<List<T>> removeAll(Class<T> clazz) {
        return Flowable.defer(() -> isNetworkAvailable()
                ? api.removeAll(clazz).flatMap(l -> db.removeAll(clazz))
                : Flowable.error(new IllegalAccessError("You are offline"))
        );
    }

    boolean isNetworkAvailable() {
        return true;
    }
}
