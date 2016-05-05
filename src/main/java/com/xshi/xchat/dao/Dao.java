package com.xshi.xchat.dao;

import java.util.List;

/**
 * Created by sheng on 4/12/2016.
 */
public interface Dao<T> {
    public void create(T entity);
    public void update(T entity);
    public void remove(T entity);
    public T find(Object id);
    public List<T> findAll();
    public List<T> findByRange(int[] range);
    public int count();
}
