package com.mola.mola.service;

import com.mola.mola.domain.User;
import com.mola.mola.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long join(User user){
        // 같은 이름이 있는 중복 회원 x
//
        long start = System.currentTimeMillis();
            if(validateDuplicatedMember(user)){
                // 중복된 회원 있음.
                return user.getId();
            }
            else{
                userRepository.create(user);
            }

            return user.getId();

    }

    private Boolean validateDuplicatedMember(User user) {
        Boolean flag = true;
        return userRepository.findByEmail(user.getEmail()).isEmpty();
    }
}
