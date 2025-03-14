package com.benjamin.onlinematatu.service.serviceImpl;

import com.benjamin.onlinematatu.DTO.MatatuDTO;
import com.benjamin.onlinematatu.entity.Matatu;
import com.benjamin.onlinematatu.repository.MatatuRepo;
import com.benjamin.onlinematatu.service.MatatuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MatatuServiceImpl implements MatatuService {

    @Autowired
    private final MatatuRepo matatuRepo;

    @Override
    public MatatuDTO convertToDTO(Matatu matatu) {
        MatatuDTO matatuDTO = new MatatuDTO();
        matatuDTO.setLicenceNumber(matatu.getLicenceNumber());
        matatuDTO.setRoute(matatu.getRoute());
        matatuDTO.setDriverName(matatu.getDriverName());
        matatuDTO.setCapacity(matatu.getCapacity());
        matatuDTO.setCurrentGpsLocation(matatu.getCurrentGpsLocation());
        return matatuDTO;
    }

    @Override
    public Matatu convertToEntity(MatatuDTO matatuDTO) {
        Matatu matatu = new Matatu();
        matatu.setLicenceNumber(matatuDTO.getLicenceNumber());
        matatu.setRoute(matatuDTO.getRoute());
        matatu.setDriverName(matatuDTO.getDriverName());
        matatu.setCapacity(matatuDTO.getCapacity());
        matatu.setCurrentGpsLocation(matatuDTO.getCurrentGpsLocation());
        return matatu;
    }

    @Override
    public List<MatatuDTO> getAllMatatus() {
        return matatuRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MatatuDTO getMatatuByLicenceNumber(String licenceNumber,MatatuDTO matatuDTO) {
        List<Matatu> matatuList=matatuRepo.findAll();

        if(matatuList.isEmpty()){
            throw new RuntimeException("Matatu list not found");
        }
            for(Matatu matatu:matatuList) {
                if (matatu.getLicenceNumber().equals(licenceNumber)) {
                    return convertToDTO(matatu);
                } else throw new RuntimeException("Licence number not found");
            }

        throw new RuntimeException("Licence number not found");


    }

    @Override
    public MatatuDTO addMatatu(MatatuDTO matatuDTO) {
        Matatu newMatatu = convertToEntity(matatuDTO);
        matatuRepo.save(newMatatu);
        return convertToDTO(newMatatu);
    }

    @Override
    public void deleteMatatuByLicenceNumber(String licenceNumber) {
        List<Matatu> matatuList=matatuRepo.findAll();
        if(matatuList.isEmpty()){
            throw new RuntimeException("Matatu list not found");

        }
            for (Matatu matatu:matatuList) {
                if (matatu.getLicenceNumber().equals(licenceNumber)) {
                    matatuRepo.delete(matatu);

                }
            }

        throw new RuntimeException("Licence number not found");



    }

    @Override
    public MatatuDTO updateMatatuByLicenseNumber(String licenceNumber, MatatuDTO matatuDTO) {
        List<Matatu> matatulist=matatuRepo.findAll();
        if(matatulist.isEmpty()){
            throw new RuntimeException("Matatu list not found");
        }
           for(Matatu matatu:matatulist) {
               if (matatu.getLicenceNumber().equals(licenceNumber)) {
                   matatu.setLicenceNumber(matatuDTO.getLicenceNumber());
                   matatu.setRoute(matatuDTO.getRoute());
                   matatu.setDriverName(matatuDTO.getDriverName());
                   matatu.setCapacity(matatuDTO.getCapacity());
                   matatu.setCurrentGpsLocation(matatuDTO.getCurrentGpsLocation());
                   matatuRepo.save(matatu);
               }
           }
        throw new RuntimeException("Licence number not found");

    }
}
