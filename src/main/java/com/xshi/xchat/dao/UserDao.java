package com.xshi.xchat.dao;

import com.xshi.xchat.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by sheng on 4/12/2016.
 */
@Repository
public class UserDao implements Dao<User>{
    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager(){
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public void create(User entity) {
        getEntityManager().persist(entity);
    }

    public void update(User entity) {
        getEntityManager().merge(entity);
    }

    public void remove(User entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public User find(Object id) {
        return getEntityManager().find(User.class,id);
    }

    public User findByName(String name){
        Query query = getEntityManager().createNamedQuery("User.findByName").setParameter("user_name", name);
        List<User> users = query.getResultList();
        if(users.size() == 1){
            return users.get(0);
        }
        return null;
    }

    public List<User> findAll() {
        Query query = getEntityManager().createQuery("select u from User u");
        List<User> users = (List<User>)query.getResultList();
        return users;
    }

    public List<User> findByRange(int[] range) {
        return null;
    }

    public int count() {
        return 0;
    }
}
