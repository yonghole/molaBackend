package com.mola.mola.service;

import com.mola.mola.domain.PointRecord;
import com.mola.mola.domain.User;
import com.mola.mola.error.ErrorCode;
import com.mola.mola.exception.EntityNotFoundException;
import com.mola.mola.exception.InvalidValueException;
import com.mola.mola.repository.PointRecordRepository;
import com.mola.mola.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.OptionalInt;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PointRecordRepository pointRecordRepository;

//    public Optional<User> login(String email, String password){
//        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXIST_ERROR));
//        String pw =
//
//        if(userRepository.check(email,password) == 0) {
//            return userRepository.findByEmail(email);
//        }
//
//        else return null;
//    }

    public void join(User user) throws DuplicatedEmailError{
        validateDuplicatedMember(user);
        userRepository.create(user);
    }

    public Optional<User> findByUserId(Long id){
        return userRepository.findByUserId(id);
    }

    private void validateDuplicatedMember(User user) throws EntityNotFoundException{
        boolean isNotDuplicated = userRepository.findByEmail(user.getEmail()).isEmpty();
        if(!isNotDuplicated) {
            throw new DuplicatedEmailError(ErrorCode.EMAIL_DUPLICATION);
        }
    }

    private void validateUserExistenceAndPoint(Long user_id, Integer point, Boolean deduct) throws EntityNotFoundException, InvalidValueException{
        Optional<User> user = userRepository.findByUserId(user_id);
        boolean isExist = user.isEmpty();
        if(isExist) {
            throw new UserNotExistError(ErrorCode.USER_NOT_EXIST_ERROR);
        }
        else{
            if(user.get().getPoint() + point < 0 && deduct){
                throw new NotEnoughPointError(ErrorCode.NOT_ENOUGH_POINT_ERROR);
            }
        }
    }



    public Integer updatePoint(Long user_id, Integer point_to_charge) throws UserNotExistError{
        validateUserExistenceAndPoint(user_id,point_to_charge,point_to_charge<0);
        PointRecord pointRecord = new PointRecord();

        User user = new User();
//        User user = userRepository.charge(user_id,point_to_charge);

//        pointRecord.setUser(user);
        String changeType = point_to_charge > 0 ? "Charge" : "Use";
        pointRecord.setChangeType(changeType);
        userRepository.charge(user_id,point_to_charge);
        validateUserExistenceAndPoint(user_id,point_to_charge,false);
        user = userRepository.findByUserId(user_id).get();

        System.out.println(user.getPoint());
        pointRecord.setUser(user);
        pointRecord.setPointBefore(user.getPoint() - point_to_charge);
        pointRecord.setPointAfter(user.getPoint());
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LocalDateTime currentDateTime = LocalDateTime.now();
        pointRecord.setPointChangeDate(currentDateTime);
        pointRecordRepository.create(pointRecord);
        return user.getPoint();
    }

    public static class NotEnoughPointError extends InvalidValueException{
        public NotEnoughPointError(ErrorCode errorCode) {super(errorCode);}
    }

    public static class DuplicatedEmailError extends InvalidValueException{
        public DuplicatedEmailError(ErrorCode errorCode) {
            super(errorCode);
        }
    }

    public static class UserNotExistError extends EntityNotFoundException{
        public UserNotExistError(ErrorCode errorCode) { super(errorCode); }
    }

}
