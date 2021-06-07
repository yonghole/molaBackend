package com.mola.mola.service;


import com.mola.mola.domain.PointRecord;
import com.mola.mola.repository.PointRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PointRecordService {

    private final PointRecordRepository pointRecordRepository;

    public PointRecordService(PointRecordRepository pointRecordRepository) {
        this.pointRecordRepository = pointRecordRepository;
    }

    public void update(PointRecord pointRecord){
        pointRecordRepository.create(pointRecord);
    }

}
