package com.telemedicine.doctor_service.service;

import com.telemedicine.doctor_service.data.entity.Doctor;
import com.telemedicine.doctor_service.data.repository.DoctorRepository;
import com.telemedicine.doctor_service.dto.CreateDoctorDto;
import com.telemedicine.doctor_service.dto.DoctorDto;
import com.telemedicine.doctor_service.dto.SkeletonDoctorDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setFirstName("John");
        doctor.setLastName("Doe");
        doctor.setEmail("john@example.com");
        doctor.setPhoneNumber("+911234567890");
        doctor.setGender("Male");
        doctor.setDateOfBirth(LocalDate.of(1990, 1, 1));
        doctor.setAddress("Some address");
        doctor.setSpecialisation("Cardiology");
        doctor.setStatus("Active");
    }

    @Test
    void getAllDoctors_returnsList() {
        when(doctorRepository.findAll()).thenReturn(List.of(doctor));

        List<Doctor> result = doctorService.getAllDoctors();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("john@example.com", result.get(0).getEmail());
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    void createDoctor_mapsDtoAndSaves() {
        CreateDoctorDto dto = new CreateDoctorDto();
        dto.setFirstName("Alice");
        dto.setLastName("Smith");
        dto.setEmail("alice@example.com");
        dto.setPhoneNumber("+911112223334");
        dto.setGender("Female");
        dto.setAddress("City");
        dto.setDateOfBirth(LocalDate.of(1985, 5, 20));

        when(doctorRepository.save(any(Doctor.class))).thenAnswer(invocation -> {
            Doctor saved = invocation.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        Doctor savedDoctor = doctorService.createDoctor(dto);

        assertNotNull(savedDoctor);
        assertEquals(10L, savedDoctor.getId());
        assertEquals("alice@example.com", savedDoctor.getEmail());
        assertEquals("Active", savedDoctor.getStatus());

        ArgumentCaptor<Doctor> captor = ArgumentCaptor.forClass(Doctor.class);
        verify(doctorRepository).save(captor.capture());
        Doctor passed = captor.getValue();
        assertEquals("Alice", passed.getFirstName());
        assertEquals("Smith", passed.getLastName());
    }

    @Test
    void createDoctorSkeleton_setsDefaultFields() {
        SkeletonDoctorDto skeleton = new SkeletonDoctorDto();
        skeleton.setFirstName("Skel");
        skeleton.setLastName("Doc");
        skeleton.setEmail("skel@example.com");
        skeleton.setSpecialisation("Neuro");

        when(doctorRepository.save(any(Doctor.class))).thenAnswer(invocation -> {
            Doctor d = invocation.getArgument(0);
            d.setId(5L);
            return d;
        });

        Doctor created = doctorService.createDoctorSkeleton(skeleton);

        assertNotNull(created);
        assertEquals(5L, created.getId());
        assertEquals("skel@example.com", created.getEmail());
        assertEquals("Pending Setup", created.getStatus());
        assertEquals("None", created.getSpecialisation());
    }

    @Test
    void validateDoctorWithId_returnsDoctorIfFound() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        Doctor result = doctorService.validateDoctorWithId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void validateDoctorWithId_returnsEmptyDoctorIfNotFound() {
        when(doctorRepository.findById(99L)).thenReturn(Optional.empty());

        Doctor result = doctorService.validateDoctorWithId(99L);

        assertNotNull(result);
        assertNull(result.getId());
    }

    @Test
    void getDoctorProfile_returnsDto() {
        when(doctorRepository.findByEmail("john@example.com")).thenReturn(Optional.of(doctor));

        DoctorDto dto = doctorService.getDoctorProfile("john@example.com");

        assertNotNull(dto);
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("john@example.com", dto.getEmail());
    }

    @Test
    void getDoctorEmailById_returnsEmail() {
        when(doctorRepository.findEmailById(1L)).thenReturn(Optional.of("john@example.com"));

        String email = doctorService.getDoctorEmailById(1L);

        assertEquals("john@example.com", email);
    }

    @Test
    void getDoctorDetailsById_returnsSkeletonDto() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        SkeletonDoctorDto dto = doctorService.getDoctorDetailsById(1L);

        assertNotNull(dto);
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("john@example.com", dto.getEmail());
        assertEquals("Cardiology", dto.getSpecialisation());
    }
}
