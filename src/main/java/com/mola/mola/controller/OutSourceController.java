package com.mola.mola.controller;

import com.mola.mola.domain.OutSource;
import com.mola.mola.error.ErrorCode;
import com.mola.mola.error.ErrorResponse;
import com.mola.mola.exception.BusinessException;
import com.mola.mola.repository.OutSourceRepository;
import com.mola.mola.service.OutSourceService;
import com.mola.mola.service.UserService;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("outsource/")
public class OutSourceController {
    private OutSourceService outSourceService;

    @Autowired
    public OutSourceController(OutSourceService outSourceService) {
        this.outSourceService = outSourceService;
    }

    @GetMapping("test/")
    public String hello(){
        return "OutSource";
    }

    @PostMapping("submit/")
    public ResponseEntity<RegisterOutSourceResponse> submit(@RequestBody @Valid RegisterOutSourceRequest registerOutSourceRequest) throws ParseException {
        OutSource os = new OutSource();
        os.setUser_id(Long.parseLong(registerOutSourceRequest.getUser_id()));
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date to = transFormat.parse(registerOutSourceRequest.getCreation_date());
        os.setCreation_date(to);
        os.setRequirements(registerOutSourceRequest.getRequirements());
        os.setCredit(Long.parseLong(registerOutSourceRequest.getCredit()));
        os.setTitle(registerOutSourceRequest.getTitle());
        if(!registerOutSourceRequest.getImg_completed().isEmpty()){
            os.setImg_completed(Long.parseLong(registerOutSourceRequest.getImg_completed()));
        }
        else{
            os.setImg_completed(Long.parseLong("0"));
        }
        if(!registerOutSourceRequest.getImg_total().isEmpty()){
            os.setImg_total(Long.parseLong(registerOutSourceRequest.getImg_total()));
        }
        else{
            os.setImg_total(Long.parseLong("0"));
        }
        outSourceService.register(os);
        RegisterOutSourceResponse response = new RegisterOutSourceResponse();
        response.setStatus(200);

        return new ResponseEntity<RegisterOutSourceResponse>(response,HttpStatus.OK);
    }

    @Getter
    public static class RegisterOutSourceRequest{
        @NotEmpty
        private String user_id;

        @DateTimeFormat
        private String creation_date;

        @NotEmpty
        private String requirements;

        @NotEmpty
        private String credit;

        @NotEmpty
        private String title;

        private String img_completed;

        private String img_total;
    }

    @Data
    public static class RegisterOutSourceResponse{
        private int status = 200;
    }

    @PostMapping("searchUserOS")
    public List<OutSource> searchOutSource(@RequestBody Map<String, Object> m){
        Long user_id = Long.parseLong(m.get("user_id").toString());

        return outSourceService.search(user_id);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final OutSourceService.UserNotExistError e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }
}
