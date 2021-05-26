package com.mola.mola.repository;

import com.mola.mola.domain.OutSource;

import java.util.List;
import java.util.Optional;

public interface OutSourceRepository {
    Boolean create(OutSource outsource);
    List<OutSource> findAll();
    Optional<OutSource> findByUserID(int ID);
}
