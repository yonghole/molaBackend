package com.mola.mola.repository;

import com.mola.mola.controller.OutSourceController;
import com.mola.mola.domain.OutSource;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


public interface OutSourceRepository {
    Long create(OutSource outSource);
    List<OutSource> findAll();
    List<OutSourceController.OutSourceDto> findByUserID(Long id);
    Optional<OutSource> findByID(Long id);
}
