package com.benjamin.onlinematatu.service;

import com.benjamin.onlinematatu.DTO.MatatuDTO;
import com.benjamin.onlinematatu.entity.Matatu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MatatuService {
    public MatatuDTO convertToDTO(Matatu matatu);
    public Matatu convertToEntity(MatatuDTO matatuDTO);
    public List<MatatuDTO> getAllMatatus();
    public MatatuDTO getMatatuByLicenceNumber(String licenceNumber, MatatuDTO matatuDTO);
    public MatatuDTO addMatatu(MatatuDTO matatuDTO);
    public void deleteMatatuByLicenceNumber(String licenceNumber);
    public MatatuDTO updateMatatuByLicenseNumber(String licenceNumber, MatatuDTO matatuDTO);
}
