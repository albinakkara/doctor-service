package com.telemedicine.doctor_service.controller;

import com.telemedicine.doctor_service.data.entity.Doctor;
import com.telemedicine.doctor_service.dto.SkeletonDoctorDto;
import com.telemedicine.doctor_service.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/validate/{id}")
    public ResponseEntity<Boolean> validateDoctorWithId(@PathVariable("id") Long id){
        Doctor doctor = doctorService.validatePatientWithId(id);
        boolean isValid = doctor.getId()!=null && doctor.getId().equals(id);
        if(!isValid){
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/email/{id}")
    public ResponseEntity<String> getDoctorEmailById(@PathVariable("id") Long id){
        String doctorEmail = doctorService.getDoctorEmailById(id);
        return ResponseEntity.ok(doctorEmail);
    }
}
