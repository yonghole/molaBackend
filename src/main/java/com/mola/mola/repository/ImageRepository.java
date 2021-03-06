package com.mola.mola.repository;

import com.mola.mola.domain.Image;

import java.sql.SQLException;
import java.util.Optional;
import java.util.OptionalInt;

public interface ImageRepository {
    Image createImage(String url);
    Optional<Image> findUndoneImageRandomly();
    void saveImage(Image image);
    Optional<Image> findImageById(Long id);
}
