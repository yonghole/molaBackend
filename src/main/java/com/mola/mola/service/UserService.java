package com.mola.mola.service;

import com.mola.mola.domain.User;
import com.mola.mola.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional <User> login(String email, String password){

            if(userRepository.check(email,password) == 0) {
                return userRepository.findByEmail(email);
//                return true;
            }
            else return null;

        //return true;
    }

    public Boolean join(User user){
        // 같은 이름이 있는 중복 회원 x
//
//        long start = System.currentTimeMillis();
            if(!validateDuplicatedMember(user)){
                // 중복된 회원 있음.
                System.out.println(user.getEmail() + " already exists!\n");

                return false;
            }
            else{
                System.out.println("crating");
                userRepository.create(user);
            }

            return true;

    }

    private Boolean validateDuplicatedMember(User user) {
        Boolean flag = true;
        return userRepository.findByEmail(user.getEmail()).isEmpty();
    }
}
