package com.telemedicine.doctor_service.data.repository;

import com.telemedicine.doctor_service.data.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);


    @Query("select d.email from Doctor d where d.id = :id")
    Optional<String> findEmailById(@Param("id") Long id);

}
