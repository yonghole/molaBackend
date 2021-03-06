package com.mola.mola.controller;

import com.mola.mola.domain.Image;
import com.mola.mola.domain.OutSource;
import com.mola.mola.domain.User;
import com.mola.mola.service.OutSourceService;
import com.mola.mola.service.UserService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("outsource/")
@RequiredArgsConstructor
public class OutSourceController {

    private final OutSourceService outSourceService;
    private final UserService userService;

    @GetMapping("test/")
    public String hello(){
        return "OutSource";
    }

    @PostMapping("submit/")
    public ResponseEntity<RegisterOutSourceResponse> submit(@RequestBody @Valid RegisterOutSourceRequest registerOutSourceRequest)
            throws ParseException {

        // 새로 만들 OS 객체
        OutSource os = new OutSource();

        // 기존에 존재하는 user 객체
        User user = userService.findByUserId(registerOutSourceRequest.getUserId()).orElseThrow();
        os.setUser(user);

        // request에 들어있는 값들 처리
        LocalDate date = LocalDate.parse(registerOutSourceRequest.getCreationDate(), DateTimeFormatter.ISO_DATE);

        os.setOutsourceCreationDate(date);
        os.setRequirements(registerOutSourceRequest.getRequirements());
        os.setCredit(registerOutSourceRequest.getCredit());
        os.setTitle(registerOutSourceRequest.getTitle());
        os.setImgCompleted(-1L);
        os.setImgTotal(-1L);

        Long id = outSourceService.register(os);

        RegisterOutSourceResponse response = new RegisterOutSourceResponse();
        response.setStatus(200);
        response.setOutsourceId(id);

        return new ResponseEntity<RegisterOutSourceResponse>(response,HttpStatus.OK);
    }

    @Getter
    public static class RegisterOutSourceRequest{
        @NotNull
        private Long userId;

        @DateTimeFormat
        private String creationDate;

        @NotEmpty
        private String requirements;

        @NotNull
        private Long credit;

        @NotEmpty
        private String title;
    }

    @Data
    public static class RegisterOutSourceResponse{
        private int status = 200;
        private long outsourceId;
    }

    @PostMapping("/searchUserOSList") //
    public ResponseEntity<SearchOutSourceResponse> searchOutSource(@RequestBody @Valid SearchOutSourceRequest searchOutSourceRequest){
        Long user_id = (searchOutSourceRequest.getUser_id());
        SearchOutSourceResponse response = new SearchOutSourceResponse();
        response.setOutSources(outSourceService.search(user_id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Getter
    public static class SearchOutSourceRequest{
        @NotNull
        private Long user_id;
    }

    @Data
    public static class OutSourceDto{
        private Long id;
        private String requirements;
        private Long imgTotal;
        private Long imgCompleted;
        private Long credit;
        private String title;
        private List<Image> completedImageList;

        public OutSourceDto(OutSource outSource) {
            this.id = outSource.getId();
            this.requirements = outSource.getRequirements();
            this.imgTotal = outSource.getImgTotal();
            this.imgCompleted = outSource.getImgCompleted();
            this.credit = outSource.getCredit();
            this.title = outSource.getTitle();
        }
    }

    @Data
    public static class SearchOutSourceResponse{

        private Integer status = 200;
        private List<OutSourceDto> outSources;

    }

    @GetMapping("/searchUserOSList/{outsource-id}")
    public ResponseEntity<GetOutSourceResponse> getOutSourceInfo(@PathVariable("outsource-id") Long outsourceId){
            GetOutSourceResponse response = new GetOutSourceResponse();

            OutSource outSource = outSourceService.getOutsourceById(outsourceId);
            Image image = outSourceService.randomlyGetDoneImageOf(outSource);

            response.setOutSourceInfo(outSource);
            response.setRandomImageInfo(image);

            return new ResponseEntity<GetOutSourceResponse>(response, HttpStatus.OK);
    }

    @Getter
    public static class GetOutSourceRequest{
        @NotNull
        private Long outsourceId;
    }

    @Data
    public static class GetOutSourceResponse{
        private Integer status = 200;
        private OutSource outSourceInfo;
        private Image randomImageInfo;
    }

//    @ExceptionHandler(BusinessException.class)
//    protected ResponseEntity<ErrorResponse> handleBusinessException(final OutSourceService.UserNotExistError e) {
//        final ErrorCode errorCode = e.getErrorCode();
//        final ErrorResponse response = ErrorResponse.of(errorCode);
//        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
//    }

}
