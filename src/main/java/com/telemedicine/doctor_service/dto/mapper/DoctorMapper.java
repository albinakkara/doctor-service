package com.telemedicine.doctor_service.dto.mapper;

import com.telemedicine.doctor_service.data.entity.Doctor;
import com.telemedicine.doctor_service.dto.DoctorDto;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public static DoctorDto toDto(Doctor entity) {
        if (entity == null) return null;

        DoctorDto dto = new DoctorDto();
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setGender(entity.getGender());
        dto.setAddress(entity.getAddress());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    // DTO → Entity
    public static Doctor toEntity(DoctorDto dto) {
        if (dto == null) return null;

        Doctor entity = new Doctor();
        entity.setFirstName(trimOrNull(dto.getFirstName()));
        entity.setLastName(trimOrNull(dto.getLastName()));
        entity.setEmail(trimOrNull(dto.getEmail()));
        entity.setPhoneNumber(trimOrNull(dto.getPhoneNumber()));
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setGender(trimOrNull(dto.getGender()));
        entity.setAddress(trimOrNull(dto.getAddress()));
        entity.setStatus(trimOrDefault(dto.getStatus(), "Active"));
        entity.setSpecialisation(dto.getSpecialisation());
        return entity;
    }


    private static String trimOrNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    private static String trimOrDefault(String s, String defaultValue) {
        String t = trimOrNull(s);
        return t != null ? t : defaultValue;
    }


}
