package ru.diasoft.nixson.service;

public interface CRUDService<T> extends RDService<T> {
    void insert(String parentId, String name);

    void update(String id, String name);
}
