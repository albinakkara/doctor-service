package com.telemedicine.doctor_service.controller;

import com.telemedicine.doctor_service.data.entity.Doctor;
import com.telemedicine.doctor_service.dto.SkeletonDoctorDto;
import com.telemedicine.doctor_service.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InternalDoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private InternalDoctorController internalDoctorController;

    @Test
    void createDoctorSkeleton_success_returnsTrue() {
        SkeletonDoctorDto skeleton = new SkeletonDoctorDto();
        skeleton.setFirstName("Skel");
        skeleton.setLastName("Doc");
        skeleton.setEmail("skel@example.com");

        Doctor saved = new Doctor();
        saved.setId(1L);

        when(doctorService.createDoctorSkeleton(any(SkeletonDoctorDto.class))).thenReturn(saved);

        ResponseEntity<Boolean> response = internalDoctorController.createDoctorSkeleton(skeleton);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    void validateDoctorWithId_valid_returnsTrueOk() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);

        when(doctorService.validateDoctorWithId(1L)).thenReturn(doctor);

        ResponseEntity<Boolean> response = internalDoctorController.validateDoctorWithId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    void validateDoctorWithId_invalid_returnsFalseNotFound() {
        Doctor emptyDoctor = new Doctor(); // id is null

        when(doctorService.validateDoctorWithId(99L)).thenReturn(emptyDoctor);

        ResponseEntity<Boolean> response = internalDoctorController.validateDoctorWithId(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody());
    }

    @Test
    void getDoctorEmailById_returnsEmail() {
        when(doctorService.getDoctorEmailById(1L)).thenReturn("doc@example.com");

        ResponseEntity<String> response = internalDoctorController.getDoctorEmailById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("doc@example.com", response.getBody());
    }

    @Test
    void getDoctorDetailsById_returnsSkeletonDto() {
        SkeletonDoctorDto dto = new SkeletonDoctorDto();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setEmail("john@example.com");

        when(doctorService.getDoctorDetailsById(1L)).thenReturn(dto);

        ResponseEntity<SkeletonDoctorDto> response = internalDoctorController.getDoctorDetailsById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("john@example.com", response.getBody().getEmail());
    }
}
