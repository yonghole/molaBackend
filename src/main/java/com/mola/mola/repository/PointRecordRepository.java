package com.mola.mola.repository;


import com.mola.mola.domain.PointRecord;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public interface PointRecordRepository {

   void create(PointRecord pointRecord);
   List<PointRecord> search(Long user_id);
}
