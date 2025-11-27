package com.telemedicine.doctor_service.service;

import com.telemedicine.doctor_service.data.entity.Doctor;
import com.telemedicine.doctor_service.data.repository.DoctorRepository;
import com.telemedicine.doctor_service.dto.CreateDoctorDto;
import com.telemedicine.doctor_service.dto.DoctorDto;
import com.telemedicine.doctor_service.dto.SkeletonDoctorDto;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.LocalDate;
import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    DoctorService(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }

    public Doctor createDoctor(CreateDoctorDto doctorDto) {

        Doctor doctor = new Doctor();
        doctor.setFirstName(doctorDto.getFirstName());
        doctor.setLastName(doctorDto.getLastName());
        doctor.setEmail(doctorDto.getEmail());
        doctor.setPhoneNumber(doctorDto.getPhoneNumber());
        doctor.setDateOfBirth(doctorDto.getDateOfBirth());
        doctor.setGender(doctorDto.getGender());
        doctor.setAddress(doctorDto.getAddress());
        doctor.setStatus("Active");

        return doctorRepository.save(doctor);

    }

    public Doctor createDoctorSkeleton(SkeletonDoctorDto skeletonDoctorDto) {

        Doctor doctor = new Doctor();
        doctor.setEmail(skeletonDoctorDto.getEmail());
        doctor.setFirstName(skeletonDoctorDto.getFirstName());
        doctor.setLastName(skeletonDoctorDto.getLastName());
        doctor.setPhoneNumber("0000000000");  // Or null + remove NOT NULL
        doctor.setGender("UNKNOWN");
        doctor.setDateOfBirth(LocalDate.of(1900, 1, 1));
        doctor.setSpecialisation("None");
        doctor.setStatus("Pending Setup");

        return doctorRepository.save(doctor);

    }

    public Doctor updateDoctor(Long id, DoctorDto dto) {
        Doctor doctor = doctorRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        if (dto.getFirstName() != null) doctor.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) doctor.setLastName(dto.getLastName());
        if (dto.getEmail() != null) doctor.setEmail(dto.getEmail());
        if (dto.getPhoneNumber() != null) doctor.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getDateOfBirth() != null) doctor.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getGender() != null) doctor.setGender(dto.getGender());
        if (dto.getAddress() != null) doctor.setAddress(dto.getAddress());
        if (dto.getStatus() != null) doctor.setStatus(dto.getStatus());

        return doctorRepository.save(doctor);
    }

    public Doctor validatePatientWithId(Long id) {
        return doctorRepository.findById(id).orElse(new Doctor());
    }
}
