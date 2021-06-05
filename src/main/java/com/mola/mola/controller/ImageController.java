package com.mola.mola.controller;

import com.mola.mola.domain.Image;
import com.mola.mola.service.ImageService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

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
    public ResponseEntity<SetNewImageInformationResponse> setImageInformation(@PathVariable(value = "image-id") Long imageId, @RequestBody SetNewImageInformationRequest newImageInfo){
        imageService.saveImage(imageId, newImageInfo);
        return new ResponseEntity<SetNewImageInformationResponse>(new SetNewImageInformationResponse(),HttpStatus.OK);
    }

    @Data
    public static class SetNewImageInformationRequest{
        @NotNull
        private Long userId;
        @NotNull
        private Double yCoordinate;
        @NotNull
        private Double xCoordinate;
        @NotNull
        private Double height;
        @NotNull
        private Double width;
    }

    @Data
    public static class SetNewImageInformationResponse {
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
