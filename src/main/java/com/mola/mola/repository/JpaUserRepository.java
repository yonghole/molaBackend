package com.mola.mola.repository;

import com.mola.mola.domain.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaUserRepository implements UserRepository{

    private final EntityManager em;

    public JpaUserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public int check(String email, String password) {
        User pass = em.createQuery("select u from User u where u.email= :email",User.class).setParameter("email",email).getSingleResult();
        if(pass == null) return 1;
        return pass.getPassword().compareTo(password);
    }

    @Override
    public User create(User user) {
        System.out.println("query!\n");
        em.persist(user);
        return user;
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> result = em.createQuery("select u from User u where u.email = :email",User.class).setParameter("email",email).getResultList();
        return result.stream().findAny();
    }

    @Override
    public Optional<User> findByUserId(Long user_id){
        List<User> result = em.createQuery("select u from User u where u.id = :id",User.class).setParameter("id",user_id).getResultList();
        return result.stream().findAny();
    }
}
