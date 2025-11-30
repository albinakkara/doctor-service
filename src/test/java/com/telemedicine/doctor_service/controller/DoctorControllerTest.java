package com.telemedicine.doctor_service.controller;

import com.telemedicine.doctor_service.data.entity.Doctor;
import com.telemedicine.doctor_service.dto.CreateDoctorDto;
import com.telemedicine.doctor_service.dto.DoctorDto;
import com.telemedicine.doctor_service.service.DoctorService;
import com.telemedicine.doctor_service.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController doctorController;

    @Test
    void getAllDoctors_returnsOk() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        when(doctorService.getAllDoctors()).thenReturn(List.of(doctor));

        ResponseEntity<List<Doctor>> response = doctorController.getAllDoctors();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getDoctorProfile_validHeader_returnsOk() {
        DoctorDto dto = new DoctorDto();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setEmail("john@example.com");

        when(doctorService.getDoctorProfile("john@example.com")).thenReturn(dto);

        ResponseEntity<DoctorDto> response = doctorController.getDoctorProfile("john@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("john@example.com", response.getBody().getEmail());
    }

    @Test
    void getDoctorProfile_blankHeader_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> doctorController.getDoctorProfile(""));
    }

    @Test
    void createDoctor_success_returnsCreated() {
        CreateDoctorDto dto = new CreateDoctorDto();
        dto.setFirstName("Alice");
        dto.setLastName("Smith");
        dto.setEmail("alice@example.com");
        dto.setPhoneNumber("+911112223334");
        dto.setGender("Female");
        dto.setAddress("City");
        dto.setDateOfBirth(LocalDate.of(1985, 5, 20));

        Doctor saved = new Doctor();
        saved.setId(1L);

        when(doctorService.createDoctor(any(CreateDoctorDto.class))).thenReturn(saved);

        ResponseEntity<String> response = doctorController.createDoctor(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(Constants.DOCTOR_CREATION_SUCCESS, response.getBody());
    }

    @Test
    void updateDoctor_success_returnsCreated() {
        DoctorDto dto = new DoctorDto();
        dto.setFirstName("Updated");

        Doctor updated = new Doctor();
        updated.setId(1L);
        updated.setFirstName("Updated");

        when(doctorService.updateDoctor(1L, dto)).thenReturn(updated);

        ResponseEntity<String> response = doctorController.updateDoctor(1L, dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(Constants.DOCTOR_UPDATE_SUCCESS, response.getBody());
    }
}
