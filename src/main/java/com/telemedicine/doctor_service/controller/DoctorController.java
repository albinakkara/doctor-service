package com.telemedicine.doctor_service.controller;

import com.telemedicine.doctor_service.data.entity.Doctor;
import com.telemedicine.doctor_service.dto.CreateDoctorDto;
import com.telemedicine.doctor_service.dto.DoctorDto;
import com.telemedicine.doctor_service.service.DoctorService;
import com.telemedicine.doctor_service.util.Constants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    DoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }

    @GetMapping("")
    public ResponseEntity<List<Doctor>> getAllDoctors(){
        return new ResponseEntity<>(doctorService.getAllDoctors(), HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<DoctorDto> getDoctorProfile(@RequestHeader(value = "X-User-Email", required = false) String email){
        if(email.isBlank()){
            throw new RuntimeException("Email not present in the header!");
        }
        return ResponseEntity.ok(doctorService.getDoctorProfile(email));
    }

    @PostMapping("")
    public ResponseEntity<String> createDoctor(@Valid @RequestBody CreateDoctorDto doctorDto){
        Doctor doctor = doctorService.createDoctor(doctorDto);
        if(doctor == null){
            throw new RuntimeException(Constants.DOCTOR_CREATION_FAILURE);
        }
        return new ResponseEntity<>(Constants.DOCTOR_CREATION_SUCCESS, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateDoctor(@PathVariable Long id, @RequestBody DoctorDto doctorDto){
        Doctor doctor = doctorService.updateDoctor(id, doctorDto);
        if(doctor == null){
            throw new RuntimeException(Constants.DOCTOR_UPDATE_FAILURE);
        }
        return new ResponseEntity<>(Constants.DOCTOR_UPDATE_SUCCESS, HttpStatus.CREATED);
    }

}
