package com.mola.mola.controller;

import com.mola.mola.domain.OutSource;
import com.mola.mola.domain.OutSourceInbound;
import com.mola.mola.error.ErrorCode;
import com.mola.mola.error.ErrorResponse;
import com.mola.mola.exception.BusinessException;
import com.mola.mola.repository.OutSourceRepository;
import com.mola.mola.service.OutSourceService;
import com.mola.mola.service.UserService;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<RegisterOutSourceResponse> register(@RequestBody @Valid RegisterOutSourceRequest registerOutSourceRequest) throws ParseException {
        OutSourceInbound os = new OutSourceInbound();
        os.setUser_id(Long.parseLong(registerOutSourceRequest.getUser_id()));
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date to = transFormat.parse(registerOutSourceRequest.getCreation_date());
        os.setcreation_date(to);
        os.setRequirements(registerOutSourceRequest.getRequirements());
        outSourceService.register(os);
        RegisterOutSourceResponse response = new RegisterOutSourceResponse();
        response.setStatus(200);

        return new ResponseEntity<RegisterOutSourceResponse>(response,HttpStatus.OK);
    }

    @Getter
    public static class RegisterOutSourceRequest{
        @NotEmpty
        private String user_id;

        @NotEmpty
        private String creation_date;

        @NotEmpty
        private String requirements;
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
