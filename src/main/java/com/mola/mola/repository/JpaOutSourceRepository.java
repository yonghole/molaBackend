package com.mola.mola.repository;

import com.mola.mola.domain.OutSource;
import com.mola.mola.domain.OutSourceInbound;
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
    public ResponseEntity<OutSource> create(OutSourceInbound outSourceInbound) {
        User user = null;
        try{
            user = em.createQuery("select u from User u where u.id = :id",User.class).setParameter("id",outSourceInbound.getUser_id()).getSingleResult();
            System.out.println("Here");
            OutSource os = new OutSource();
            os.setUser_id((outSourceInbound.getUser_id()));
            os.setCreation_date(outSourceInbound.getcreation_date());
            System.out.println(os.getCreation_date());
            em.persist(os);
            Requirements rqs = new Requirements();
            rqs.setOs_id(os.getId());
            rqs.setRequirements(outSourceInbound.getRequirements());
            em.persist(rqs);
            System.out.println(os.getId());
            return new ResponseEntity<>(os,HttpStatus.OK);
        }catch (NoResultException nre){
            OutSource os = new OutSource();
            os.setUser_id((outSourceInbound.getid()));
            os.setCreation_date(outSourceInbound.getcreation_date());
            return new ResponseEntity<>(os,HttpStatus.NOT_FOUND);
        }



//        em.persist(outsource);
//        return true;
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
