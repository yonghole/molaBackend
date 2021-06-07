package com.mola.mola.repository;

import com.mola.mola.domain.PointRecord;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class JpaPointRecordRepository implements PointRecordRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(PointRecord pointRecord) {
        em.persist(pointRecord);
    }

    @Override
    public List<PointRecord> search(Long user_id) {
        return em.createQuery("SELECT p from PointRecord p where p.user.id = :id",PointRecord.class).setParameter("id",user_id).getResultList();
    }
}
