package com.mola.mola.repository;

import com.mola.mola.domain.Image;

import java.util.Optional;

public interface ImageRepository {
    Image createImage(String url);
    Optional<Image> findRandomImage();
}
