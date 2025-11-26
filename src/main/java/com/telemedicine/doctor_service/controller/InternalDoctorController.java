package com.telemedicine.doctor_service.controller;

import com.telemedicine.doctor_service.data.entity.Doctor;
import com.telemedicine.doctor_service.dto.SkeletonDoctorDto;
import com.telemedicine.doctor_service.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/doctors")
public class InternalDoctorController {

    private final DoctorService doctorService;

    public InternalDoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }

    @PostMapping("")
    public ResponseEntity<Boolean> createDoctorSkeleton(@Valid @RequestBody SkeletonDoctorDto skeletonDoctorDto){
        Doctor doctor = doctorService.createDoctorSkeleton(skeletonDoctorDto);
        if(doctor == null){
            throw new RuntimeException("Doctor can not be created");
        }
        return ResponseEntity.ok(true);
    }
}
