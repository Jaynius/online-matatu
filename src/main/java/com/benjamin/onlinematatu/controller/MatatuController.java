package com.benjamin.onlinematatu.controller;


import com.benjamin.onlinematatu.DTO.MatatuDTO;
import com.benjamin.onlinematatu.entity.Matatu;
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

    @GetMapping("/{licenceNumber}")
    public ResponseEntity<MatatuDTO> getMatatuByLicenceNumber(@PathVariable String licenceNumber) {
        MatatuDTO matatu=matatuService.getMatatuByLicenceNumber(licenceNumber);
        return new ResponseEntity<>(matatu, HttpStatus.OK);
    }

    @DeleteMapping("/{licenceNumber}")
    public ResponseEntity<Void> deleteMatatu(@PathVariable String licenceNumber) {
        matatuService.deleteMatatuByLicenceNumber(licenceNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{licenceNumber}")
    public ResponseEntity<MatatuDTO> updateMatatu(@PathVariable String licenceNumber, @RequestBody MatatuDTO matatuDTO) {
        MatatuDTO matatu=matatuService.updateMatatuByLicenseNumber(licenceNumber, matatuDTO);
        return new ResponseEntity<>(matatu, HttpStatus.OK);
    }
}
