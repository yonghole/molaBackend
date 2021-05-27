package com.mola.mola.service;

import com.mola.mola.domain.OutSource;
import com.mola.mola.domain.OutSourceInbound;
import com.mola.mola.domain.User;
import com.mola.mola.error.ErrorCode;
import com.mola.mola.exception.InvalidValueException;
import com.mola.mola.repository.OutSourceRepository;
import com.mola.mola.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class OutSourceService {

    private final OutSourceRepository outSourceRepository;
    private final UserRepository userRepository;

    public OutSourceService(OutSourceRepository outSourceRepository, UserRepository userRepository) {

        this.outSourceRepository = outSourceRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<OutSource> register(OutSource outSource){
        validateNotExistMember(outSource.getUser_id());
        return outSourceRepository.create(outSource);
    }

    private void validateNotExistMember(Long user_id) {
        boolean userExists =  userRepository.findByUserId(user_id).isEmpty();
        if(userExists) {
            throw new OutSourceService.UserNotExistError(ErrorCode.USER_NOT_EXIST_ERROR);
        }
    }

    public static class UserNotExistError extends InvalidValueException {
        public UserNotExistError(ErrorCode errorCode) {
            super(errorCode);
        }
    }

//    public static class

    public List<OutSource> search(Long user_id){
        return outSourceRepository.findByUserID(user_id);
    }

}
