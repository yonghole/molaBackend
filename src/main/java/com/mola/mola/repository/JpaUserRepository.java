package com.mola.mola.repository;

import com.mola.mola.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository{

    @PersistenceContext
    private final EntityManager em;

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
        User user = em.find(User.class, user_id);
        return Optional.ofNullable(user);
    }
}
