package com.mola.mola.service;

import com.mola.mola.domain.OutSource;
import com.mola.mola.repository.OutSourceRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public class OutSourceService {

    private final OutSourceRepository outSourceRepository;

    public OutSourceService(OutSourceRepository outSourceRepository) {
        this.outSourceRepository = outSourceRepository;
    }

    public Optional<OutSource> register(OutSource outSource){
        return outSourceRepository.create(outSource);
    }

}
