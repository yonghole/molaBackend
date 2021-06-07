package com.mola.mola.repository;

import com.mola.mola.domain.Image;
import com.mola.mola.exception.InvalidValueException;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class JpaImageRepository implements ImageRepository{

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Image createImage(String url) {
        Image image = new Image();
        image.setUrl(url);
        em.persist(image);
        em.flush();
        return image;
    }

    /**
     * 작업이 이루어지지 않은 image 혹은 작업이 이루어졌지만 reject 된 image 중에서 랜덤으로 image를 반환한다.
     */
    @Override
    public Optional<Image> findUndoneImageRandomly() {

        String sql = "select image.*\n" +
                "from image\n" +
                "         left join work_history wh on image.image_id = wh.image_id\n" +
                "where wh.work_history_id IS NULL or wh.is_rejected = 1\n" +
                "ORDER BY RAND() LIMIT 1;";

        List imageList = em.createNativeQuery(sql, Image.class).getResultList();

        if(imageList.isEmpty()) return Optional.empty();
        return Optional.ofNullable((Image) imageList.get(0));
    }



    @Override
    public Optional<Image> findImageById(Long id) {
        return Optional.ofNullable(em.find(Image.class, id));
    }

    @Override
    // 이미지 정보 저장.
    public void saveImage(Image image) {
        try {
            em.persist(image);
        }catch (PersistenceException ex){
            System.out.println("1234");
            System.out.println(ex.getCause());
        }
    }
}
