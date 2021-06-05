package com.mola.mola.controller;

import com.mola.mola.error.ErrorCode;
import com.mola.mola.error.ErrorResponse;
import com.mola.mola.exception.BusinessException;
import com.mola.mola.service.OutSourceService;
import com.mola.mola.service.ReceiveFileService;
import com.mola.mola.service.S3Service;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("file/")
@RequiredArgsConstructor
public class ReceiveFileController {

   private final ReceiveFileService receiveFileService;
   private final OutSourceService outSourceService;
   private final S3Service s3Service;

   @PostMapping("upload/")
   public String saveFile(@ModelAttribute @Valid FileReceiveDto fileReceiveDto) throws IOException{
       receiveFileService.ValidateOsID(fileReceiveDto.getOutSourceId());
       receiveFileService.ValidateUserID(fileReceiveDto.getUserId());
       String filePath= receiveFileService.saveFile(fileReceiveDto.getFile(), fileReceiveDto.getUserId().toString()); // 해당 파일을 저장하고
       List<File> fileList = receiveFileService.unzipFileByFilePath(filePath); // 해당 파일들을 unzip
       outSourceService.registerImageFiles(fileList, fileReceiveDto.getOutSourceId());
       return "uploaded";
   }

   @Data
   public static class FileReceiveDto{
       private MultipartFile file;

       @NotNull
       private Long userId;

       @NotNull
       private Long outSourceId;
   }

//    @ExceptionHandler(BusinessException.class)
//    protected ResponseEntity<ErrorResponse> handleBusinessException(final ReceiveFileService.UserIdNullError e) {
//        final ErrorCode errorCode = e.getErrorCode();
//        final ErrorResponse response = ErrorResponse.of(errorCode);
//        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
//    }
}
