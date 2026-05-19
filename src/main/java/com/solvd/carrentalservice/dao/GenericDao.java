package com.solvd.carrentalservice.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {
    void create(T entity);

    Optional<T> findById(Long id);

    List<T> findAll();

    void update(T entity);

    void delete(Long id);
}
