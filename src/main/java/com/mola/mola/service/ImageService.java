package com.mola.mola.service;

import com.mola.mola.domain.Image;
import com.mola.mola.error.ErrorCode;
import com.mola.mola.exception.EntityNotFoundException;
import com.mola.mola.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public Image getRandomImage(){
        return imageRepository.findRandomImage().orElseThrow(() -> new EntityNotFoundException(ErrorCode.IMAGE_NOT_FOUND));
    }
}
