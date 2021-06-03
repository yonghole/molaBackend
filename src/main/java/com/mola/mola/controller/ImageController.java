package com.mola.mola.controller;

import com.mola.mola.domain.Image;
import com.mola.mola.service.ImageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/image")
    public ResponseEntity getRandomImage(){
        RandomImageInquiryResponse response = RandomImageInquiryResponse.of(imageService.getRandomImage());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Getter
    @Setter
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
