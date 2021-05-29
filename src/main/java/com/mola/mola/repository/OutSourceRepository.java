package com.mola.mola.repository;

import com.mola.mola.domain.OutSource;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


public interface OutSourceRepository {
    ResponseEntity<OutSource> create(OutSource outSource);
    List<OutSource> findAll();
    List<OutSource> findByUserID(Long ID);
    List<OutSource> findByID(Long Id);
}
