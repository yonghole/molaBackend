package com.mola.mola.repository;

import com.mola.mola.domain.OutSource;
import com.mola.mola.domain.Requirements;
import com.mola.mola.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JpaOutSourceRepository implements OutSourceRepository{

    private final EntityManager em;

    public JpaOutSourceRepository(EntityManager em) {
        this.em = em;
    }



    @Override
    public ResponseEntity<OutSource> create(OutSource outSource) {
        User user = null;

            System.out.println("Here");
            em.persist(outSource);
            System.out.println(outSource.getId());
            return new ResponseEntity<>(outSource,HttpStatus.OK);

    }

    @Override
    public List<OutSource> findAll() {
        return em.createQuery("select o from OutSource o",OutSource.class).getResultList();
    }

    @Override
    public List<OutSource> findByUserID(Long ID) {
        List<OutSource> result = em.createQuery("select o from OutSource o where o.user_id = :id",OutSource.class).setParameter("id",ID).getResultList();
        return result;
    }
}
