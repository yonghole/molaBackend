package com.mola.mola.service;

import com.mola.mola.controller.ImageController;
import com.mola.mola.controller.UserController;
import com.mola.mola.domain.Image;
import com.mola.mola.domain.User;
import com.mola.mola.domain.WorkHistory;
import com.mola.mola.error.ErrorCode;
import com.mola.mola.exception.EntityNotFoundException;
import com.mola.mola.exception.InvalidValueException;
import com.mola.mola.repository.ImageRepository;
import com.mola.mola.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    public Image getRandomImage(){
        return imageRepository.findRandomImage().orElseThrow(() -> new EntityNotFoundException(ErrorCode.IMAGE_NOT_FOUND));
    }

    @Transactional
    public void saveImage(Long imageId, ImageController.SetNewImageInformationRequest newImageInformationRequest){
        Image image = imageRepository.findImageById(imageId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.IMAGE_NOT_FOUND));
        User user = userRepository.findByUserId(newImageInformationRequest.getUserId()).orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXIST_ERROR));
        WorkHistory workHistory = WorkHistory.withTimeOf(image, user);

        image.setXCoordinate(newImageInformationRequest.getXCoordinate());
        image.setYCoordinate(newImageInformationRequest.getYCoordinate());
        image.setWidth(newImageInformationRequest.getWidth());
        image.setHeight(newImageInformationRequest.getHeight());
        image.setWorkHistory(workHistory);

        imageRepository.saveImage(image);
    }
}
