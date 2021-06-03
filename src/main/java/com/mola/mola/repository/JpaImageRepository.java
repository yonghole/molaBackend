package com.mola.mola.repository;

import com.mola.mola.domain.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @Override
    // 랜덤으로 하나의 image를 선택한다.
    public Optional<Image> findRandomImage() {

        String sql = "select image.*\n" +
                "from image\n" +
                "         left join work_history wh on image.image_id = wh.image_id\n" +
                "where wh.work_history_id IS NULL or wh.is_rejected = 1\n" +
                "ORDER BY RAND() LIMIT 1;";

        List imageList = em.createNativeQuery(sql, Image.class).getResultList();

        if(imageList.isEmpty()) return Optional.empty();
        return Optional.ofNullable((Image) imageList.get(0));
    }


}
