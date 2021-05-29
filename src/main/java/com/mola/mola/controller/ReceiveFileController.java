package com.mola.mola.controller;

//import com.mola.mola.service.ReceiveFileService;
//import com.mola.mola.DTO.FileUploadDto;

import com.mola.mola.error.ErrorCode;
import com.mola.mola.error.ErrorResponse;
import com.mola.mola.exception.BusinessException;
import com.mola.mola.service.ReceiveFileService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@RequestMapping("file/")
public class ReceiveFileController {

    private  ReceiveFileService receiveFileService;

    @Autowired
    public ReceiveFileController(ReceiveFileService receiveFileService) {
        this.receiveFileService = receiveFileService;
    }

    @PostMapping("upload/")
   public String saveFile(@ModelAttribute FileReceiveDto fileReceiveDto) throws IOException{
       receiveFileService.saveFile(fileReceiveDto.getFile(), fileReceiveDto.getUser_id());

       return "uploaded";
   }

   @Getter
   @Setter
   public static class FileReceiveDto{
       private MultipartFile file;

       @NotEmpty
       private String user_id;

       @NotEmpty
       private String os_id;
   }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final ReceiveFileService.UserIdNullError e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

//    @ExceptionHandler(BusinessException.class)
//    protected ResponseEntity<ErrorResponse> handleBusinessException2(final ReceiveFileService.FileAlreadyExistsError e) {
//        final ErrorCode errorCode = e.getErrorCode();
//        final ErrorResponse response = ErrorResponse.of(errorCode);
//        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
//    }


}
