package com.mola.mola.repository;


import com.mola.mola.domain.PointRecord;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public interface PointRecordRepository {

   void create(PointRecord pointRecord);

}
