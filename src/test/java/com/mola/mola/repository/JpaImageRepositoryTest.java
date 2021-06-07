package com.mola.mola.repository;

import com.mola.mola.domain.Image;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class JpaImageRepositoryTest {

    @Autowired ImageRepository imageRepository;

    @Test
    @Rollback(value = false)
    @Transactional
    public void 이미지_랜덤_조회(){
        Image image = imageRepository.findUndoneImageRandomly().orElseThrow();
        System.out.println(image.getId());
    }
}