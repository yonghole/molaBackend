package com.mola.mola.controller;

import com.mola.mola.domain.Image;
import com.mola.mola.service.ImageService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/image")
    public ResponseEntity<RandomImageInquiryResponse> getRandomImage(){
        RandomImageInquiryResponse response = RandomImageInquiryResponse.of(imageService.getUndoneImageRandomly());
        return new ResponseEntity<RandomImageInquiryResponse>(response, HttpStatus.OK);
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
        private Integer status = 200;
    }

    @GetMapping("/image/{image-id}")
    public ResponseEntity<GetImageInformationResponse> getImageInformation(@PathVariable("image-id") Long imageId){
        GetImageInformationResponse response = new GetImageInformationResponse();
        Image image = imageService.getImage(imageId);
        response.setImageInfo(ImageDto.of(image));
        return new ResponseEntity<GetImageInformationResponse>(response, HttpStatus.OK);
    }

    @Data
    public static class GetImageInformationResponse{
        private Integer status = 200;
        private ImageDto imageInfo;
    }

    @Data
    public static class ImageDto{
        private Double yCoordinate;
        private Double xCoordinate;
        private Double height;
        private Double width;
        private String url;
        private LocalDateTime workedTime;
        private Long workerId;
        private Boolean isRejected;

        public static ImageDto of(Image image){

            ImageDto imageDto = new ImageDto();

            imageDto.setHeight(image.getHeight());
            imageDto.setWidth(image.getWidth());
            imageDto.setXCoordinate(image.getXCoordinate());
            imageDto.setYCoordinate(image.getYCoordinate());
            imageDto.setUrl(imageDto.getUrl());

            if(image.getWorkHistory() != null) {
                imageDto.setWorkedTime(image.getWorkHistory().getWorkTime());
                imageDto.setWorkerId(image.getWorkHistory().getUser().getId());
                imageDto.setIsRejected(image.getWorkHistory().getIsRejected());
            }

            return imageDto;
        }

    }

}
