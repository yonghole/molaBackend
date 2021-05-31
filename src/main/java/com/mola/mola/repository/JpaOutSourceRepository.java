package com.mola.mola.repository;

import com.mola.mola.domain.OutSource;
import com.mola.mola.domain.Requirements;
import com.mola.mola.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JpaOutSourceRepository implements OutSourceRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long create(OutSource outSource) {
        em.persist(outSource);
        return outSource.getId();
    }

    @Override
    public List<OutSource> findAll() {
        return em.createQuery("select o from OutSource o",OutSource.class).getResultList();
    }

    @Override
    public Optional<OutSource> findByID(Long id){
        OutSource result = em.find(OutSource.class, id);
        return Optional.ofNullable(result);
    }

    @Override
    public List<OutSource> findByUserID(Long ID) {
        List<OutSource> result = em.createQuery("select o from OutSource o where o.user_id = :id",OutSource.class).setParameter("id",ID).getResultList();
        return result;
    }
}
