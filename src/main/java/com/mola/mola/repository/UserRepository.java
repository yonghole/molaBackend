package com.mola.mola.repository;

import com.mola.mola.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User create(User user);
    List<User> findAll();
    Optional<User> findByEmail(String email);
}
