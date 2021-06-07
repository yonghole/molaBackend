package com.mola.mola.controller;

import com.mola.mola.auth.JwtUtils;
import com.mola.mola.auth.UserDetailsImpl;
import com.mola.mola.domain.PointRecord;
import com.mola.mola.domain.User;
import com.mola.mola.error.ErrorCode;
import com.mola.mola.error.ErrorResponse;
import com.mola.mola.exception.BusinessException;
import com.mola.mola.exception.EntityNotFoundException;
import com.mola.mola.service.UserService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("user/")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("login/")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse();

        response.setEmail(userDetails.getEmail());
        response.setId(userDetails.getId());
        response.setJwt(jwt);
        response.setRole(roles);

        User user = userService.findByUserId(userDetails.getId()).orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXIST_ERROR));

        response.setName(user.getName());
        response.setPoint(user.getPoint());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("test/")
    public String login(){
        return "hello world!";
    }

    @Getter
    public static class LoginRequest{
        private String email;
        private String password;
    }

    @Data
    public static class LoginResponse{
        private String jwt;
        private Long id;
        private String email;
        private Integer point;
        private String name;
        private List<String> role;
    }

//    @Getter
//    public static class ChargePointRequest{
//        @NotNull
//        private Long user_id;
//        @NotNull
//        private Long point_to_charge;
//    }


    @PostMapping("updatePoint/{user_id}/{point_to_charge}")
    public ResponseEntity<ChargePointResponse> charge(@PathVariable("user_id") Long user_id, @PathVariable("point_to_charge") Integer point){
        ChargePointResponse chargePointResponse = new ChargePointResponse();
        chargePointResponse.setUser_id(user_id);
        chargePointResponse.setPoint(userService.updatePoint(user_id,point));

        return new ResponseEntity<>(chargePointResponse,HttpStatus.OK);
    }

    @PostMapping("searchPointHistory/{user_id}")
    public ResponseEntity<SearchPointResponse> search(@PathVariable("user_id") Long user_id){
        SearchPointResponse searchPointResponse = new SearchPointResponse();
        searchPointResponse.setPointRecord(userService.searchPointHistory(user_id));
        return new ResponseEntity<>(searchPointResponse, HttpStatus.OK);
    }


    @Data
    public static class SearchPointResponse{
        private Integer status = 200;
        private List<PointRecord> pointRecord;
    }

    @Data
    public static class ChargePointResponse{
        private Integer status = 200;
        private Long user_id;
        private Integer point;
    }

    @PostMapping("signup")
    public ResponseEntity<CreateUserResponse> create(@RequestBody @Valid CreateUserRequest createUserRequest){
       User user = new User();

       user.setEmail(createUserRequest.getEmail());
       user.setName(createUserRequest.getName());
       user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
       user.setPhoneNum(createUserRequest.getPhoneNum());
       user.setPoint(0);
       userService.join(user);

       CreateUserResponse response = new CreateUserResponse();
       response.setStatus(200);
       return new ResponseEntity<CreateUserResponse>(response ,HttpStatus.OK);
    }

    @Data
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
        private int status = 200;
    }

//    @ExceptionHandler(BusinessException.class)
//    protected ResponseEntity<ErrorResponse> handleBusinessException(final UserService.DuplicatedEmailError e) {
//        final ErrorCode errorCode = e.getErrorCode();
//        final ErrorResponse response = ErrorResponse.of(errorCode);
//        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
//    }

}
