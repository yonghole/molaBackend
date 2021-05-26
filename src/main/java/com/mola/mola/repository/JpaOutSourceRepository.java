package com.mola.mola.repository;

import com.mola.mola.domain.OutSource;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaOutSourceRepository implements OutSourceRepository{

    private final EntityManager em;

    public JpaOutSourceRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Boolean create(OutSource outsource) {
        em.persist(outsource);
        return true;
    }

    @Override
    public List<OutSource> findAll() {
        return em.createQuery("select o from outsource o",OutSource.class).getResultList();
    }

    @Override
    public Optional<OutSource> findByUserID(int ID) {
        List<OutSource> result = em.createQuery("select o from Outsource o where o.user_id = :id",OutSource.class).setParameter("id",ID).getResultList();
        return result.stream().findAny();
    }
}
