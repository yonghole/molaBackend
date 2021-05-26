package com.mola.mola.controller;

import com.mola.mola.domain.OutSource;
import com.mola.mola.domain.OutSourceInbound;
import com.mola.mola.repository.OutSourceRepository;
import com.mola.mola.service.OutSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("outsource/")
public class OutSourceController {
    private OutSourceService outSourceService;

    @Autowired
    public OutSourceController(OutSourceService outSourceService) {
        this.outSourceService = outSourceService;
    }

    @GetMapping("test/")
    public String hello(){
        return "OutSource";
    }

    @PostMapping("submit/")
    public ResponseEntity<OutSource> register(@RequestBody OutSourceInbound outSourceInbound){
        OutSource os = new OutSource();

//        os.setCreation_date(outSourceInbound.getOutsource_creation_date());
//        os.setId(outSourceInbound.getId());
//        os.setUser_id(outSourceInbound.getUser_id());

        return outSourceService.register(outSourceInbound);
    }
}
