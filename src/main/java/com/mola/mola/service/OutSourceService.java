package com.mola.mola.service;

import com.mola.mola.domain.Image;
import com.mola.mola.domain.OutSource;
import com.mola.mola.error.ErrorCode;
import com.mola.mola.exception.EntityNotFoundException;
import com.mola.mola.exception.InvalidValueException;
import com.mola.mola.repository.ImageRepository;
import com.mola.mola.repository.OutSourceRepository;
import com.mola.mola.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class OutSourceService {

    private final S3Service s3Service;
    private final OutSourceRepository outSourceRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public Long register(OutSource outSource){
        validateNotExistMember(outSource.getUser().getId());
        return outSourceRepository.create(outSource);
    }

    public OutSource registerImageFiles(List<File> imageFiles, Long outSourceId){
        OutSource outSource = outSourceRepository.findByID(outSourceId).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.OUTSOURCE_ID_INVALID_ERROR)
        );
        // 해당 outsource 조회

        List<Image> imageList = new ArrayList<>();

        for (File file : imageFiles) {
            String url = s3Service.upload(file, "outsources/" + outSourceId.toString());
            Image newImage = imageRepository.createImage(url);
            outSource.addImage(newImage);
            System.out.println(url);
        }

        return outSource;
    }

    private void validateNotExistMember(Long user_id) {
        boolean userExists =  userRepository.findByUserId(user_id).isEmpty();
        if(userExists) {
            throw new OutSourceService.UserNotExistError(ErrorCode.USER_NOT_EXIST_ERROR);
        }
    }

    public static class UserNotExistError extends InvalidValueException {
        public UserNotExistError(ErrorCode errorCode) {
            super(errorCode);
        }
    }

    public List<OutSource> search(Long user_id){
        return outSourceRepository.findByUserID(user_id);
    }

}
