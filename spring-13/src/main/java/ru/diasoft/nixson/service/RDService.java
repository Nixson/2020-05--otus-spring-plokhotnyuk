package ru.diasoft.nixson.service;

public interface RDService<T> {
    Iterable<T> getAll();

    void delete(Long id);
}
