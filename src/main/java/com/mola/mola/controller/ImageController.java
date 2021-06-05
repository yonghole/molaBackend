package com.mola.mola.controller;

import com.mola.mola.domain.Image;
import com.mola.mola.service.ImageService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/image")
    public ResponseEntity<RandomImageInquiryResponse> getRandomImage(){
        RandomImageInquiryResponse response = RandomImageInquiryResponse.of(imageService.getRandomImage());
        return new ResponseEntity<RandomImageInquiryResponse>(response, HttpStatus.OK);
    }

    @PostMapping("/image/{image-id}")
    public ResponseEntity<SetNewImageResponse> setImageInformation(@PathVariable(value = "image-id") Long imageId, @RequestBody NewImageInfo newImageInfo){
        imageService.saveImage(imageId, newImageInfo.getUserId(), newImageInfo.getXCoordinate(), newImageInfo.getYCoordinate());
        return new ResponseEntity<SetNewImageResponse>(new SetNewImageResponse(),HttpStatus.OK);
    }

    @Data
    public static class NewImageInfo{
        private Long userId;
        private Double yCoordinate;
        private Double xCoordinate;
    }

    @Data
    public static class SetNewImageResponse{
        private Integer httpStatusCode = 200;
    }

    @Data
    public static class RandomImageInquiryResponse{
        private String url;
        private Long imageId;

        public static RandomImageInquiryResponse of(Image image){
            RandomImageInquiryResponse randomImageInquiryResponse = new RandomImageInquiryResponse();
            randomImageInquiryResponse.setUrl(image.getUrl());
            randomImageInquiryResponse.setImageId(image.getId());
            return randomImageInquiryResponse;
        }
    }


}
