package com.mola.mola.controller;

import com.mola.mola.domain.User;
import com.mola.mola.error.ErrorCode;
import com.mola.mola.error.ErrorResponse;
import com.mola.mola.exception.BusinessException;
import com.mola.mola.service.UserService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@RequiredArgsConstructor
@RestController
@RequestMapping("user/")
public class UserController {

    private final UserService userService;

//    @PostMapping("login/")
//    public Optional<User> login(@RequestBody Map<String, Object> m){
//        String email = m.get("email").toString();
//        String password = m.get("password").toString();
//
//        return (userService.login(email,password));
//    }

    @PostMapping("signup")
    public ResponseEntity<CreateUserResponse> create(@RequestBody @Valid CreateUserRequest createUserRequest){
       User user = new User();

       user.setEmail(createUserRequest.getEmail());
       user.setName(createUserRequest.getName());
       user.setPassword(createUserRequest.getPassword());
       user.setPhonenum(createUserRequest.getPhoneNum());
       user.setPoint(0);
       CreateUserResponse response = new CreateUserResponse();
       userService.join(user);
       return new ResponseEntity<CreateUserResponse>(HttpStatus.OK);

    }

    @Getter
    public static class CreateUserRequest{
        @Email
        private String email;

        @NotEmpty
        private String password;

        @NotEmpty
        private String name;

        @NotEmpty
        private String phoneNum;
    }

    @Data
    public static class CreateUserResponse{
        private Boolean isSuccess;
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final UserService.DuplicatedEmailError e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }


}
