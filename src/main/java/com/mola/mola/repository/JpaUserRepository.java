package com.mola.mola.repository;

import com.mola.mola.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
//        System.out.println("query!\n");
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

    @Override
    @Modifying
    public void charge(Long user_id,Integer point_to_charge){

        Query query = em.createQuery("UPDATE User u set u.point = point + :point where u.id = :id").setParameter("id",user_id);
        query.setParameter("point",point_to_charge);
        query.executeUpdate();
        // Entity Manager Caching 때문에 clear 해줘야 함!!
        em.clear();
        em.flush();
//        return em.createQuery("SELECT u from User u where u.id = :id",User.class).setParameter("id",user_id).getSingleResult();
    }


}
