package com.mola.mola.repository;

import com.mola.mola.domain.OutSource;
import com.mola.mola.domain.OutSourceInbound;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


public interface OutSourceRepository {
    ResponseEntity<OutSource> create(OutSourceInbound outSourceInbound);
    List<OutSource> findAll();
    List<OutSource> findByUserID(Long ID);
}
