package com.example.lunchbuddyapp.mapper;

import com.example.lunchbuddyapp.dto.SessionDTO;
import com.example.lunchbuddyapp.model.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SessionMapper {
    @Mapping(target = "invitations", ignore = true)
    SessionDTO toDto(Session session);

    Session toEntity(SessionDTO sessionDTO);
}
