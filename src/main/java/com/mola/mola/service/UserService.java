package com.mola.mola.service;

import com.mola.mola.domain.User;
import com.mola.mola.error.ErrorCode;
import com.mola.mola.exception.InvalidValueException;
import com.mola.mola.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> login(String email, String password){
            if(userRepository.check(email,password) == 0) {
                return userRepository.findByEmail(email);
            }
            else return null;
    }

    public void join(User user) throws DuplicatedEmailError{
        validateDuplicatedMember(user);
        userRepository.create(user);
    }

    private void validateDuplicatedMember(User user) {
        boolean isNotDuplicated =  userRepository.findByEmail(user.getEmail()).isEmpty();
        if(!isNotDuplicated) {
            throw new DuplicatedEmailError(ErrorCode.EMAIL_DUPLICATION);
        }
    }

    public static class DuplicatedEmailError extends InvalidValueException{
        public DuplicatedEmailError(ErrorCode errorCode) {
            super(errorCode);
        }
    }

}
