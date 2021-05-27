package com.mola.mola.service;

import com.mola.mola.domain.OutSource;
import com.mola.mola.domain.OutSourceInbound;
import com.mola.mola.repository.OutSourceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class OutSourceService {

    private final OutSourceRepository outSourceRepository;

    public OutSourceService(OutSourceRepository outSourceRepository) {
        this.outSourceRepository = outSourceRepository;
    }

    public ResponseEntity<OutSource> register(OutSourceInbound outSourceInbound){
        return outSourceRepository.create(outSourceInbound);
    }

    public List<OutSource> search(Long user_id){
        return outSourceRepository.findByUserID(user_id);
    }

}
