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

    public Boolean login(String email, String password){
        if(userRepository.findByEmail(email).isEmpty()){
            System.out.println("user not exists!");
            return false;
        }
        else{
            if(userRepository.check(email,password) == 0){
                return true;
            }
            else{
                System.out.println("비밀번호 틀림!");
                return false;
            }
        }
        //return true;
    }

    public Long join(User user){
        // 같은 이름이 있는 중복 회원 x
//
        long start = System.currentTimeMillis();
            if(!validateDuplicatedMember(user)){
                // 중복된 회원 있음.
                System.out.println(user.getEmail() + " already exists!\n");

                return user.getId();
            }
            else{
                System.out.println("crating");
                userRepository.create(user);
            }

            return user.getId();

    }

    private Boolean validateDuplicatedMember(User user) {
        Boolean flag = true;
        return userRepository.findByEmail(user.getEmail()).isEmpty();
    }
}
