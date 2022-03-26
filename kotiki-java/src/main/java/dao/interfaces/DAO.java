package dao.interfaces;

import dao.tools.DAOException;

import java.util.List;

public interface DAO<T> {
    List<T> getAll();
    void add(T t) throws DAOException;
    T getById(Long id) throws DAOException;
    void update(T t) throws DAOException;
    void delete(T t) throws DAOException;
}
