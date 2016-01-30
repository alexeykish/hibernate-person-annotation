package by.pvt.kish.dao;

import by.pvt.kish.exception.DaoException;

import java.io.Serializable;

/**
 * @author Kish Alexey
 */
public interface DAO<T> {

    Serializable saveOrUpdate(T t) throws DaoException;

    void delete(Serializable id) throws DaoException;

    T get(Serializable id) throws  DaoException;

    T load(Serializable id) throws  DaoException;
}
