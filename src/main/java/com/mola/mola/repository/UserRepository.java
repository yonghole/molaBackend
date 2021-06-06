package com.mola.mola.repository;

import com.mola.mola.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User create(User user);
    int check(String email,String password);
    List<User> findAll();
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(Long user_id);
    void charge(Long user_id, Integer point_to_charge);
}
