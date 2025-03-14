package com.benjamin.onlinematatu.controller;


import com.benjamin.onlinematatu.DTO.MatatuDTO;
import com.benjamin.onlinematatu.entity.Matatu;
import com.benjamin.onlinematatu.repository.MatatuRepo;
import com.benjamin.onlinematatu.service.MatatuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matatu")
@RequiredArgsConstructor
public class MatatuController {

    @Autowired
    private final MatatuService matatuService;

    @PostMapping
    public ResponseEntity<MatatuDTO> addMatatu(@RequestBody MatatuDTO matatuDTO) {
        Matatu matatu= matatuService.convertToEntity(matatuDTO);
        return new ResponseEntity<>(matatuDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MatatuDTO>> getAllMatatu() {
        List<MatatuDTO> matatuList=matatuService.getAllMatatus();
        return new ResponseEntity<>(matatuList, HttpStatus.OK);
    }



}
