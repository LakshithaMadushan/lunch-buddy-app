package com.example.lunchbuddyapp.mapper;

import com.example.lunchbuddyapp.dto.InvitationDTO;
import com.example.lunchbuddyapp.model.Invitation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InvitationMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "session.invitations", ignore = true)
    @Mapping(target = "user.invitations", ignore = true)
    InvitationDTO toDto(Invitation invitation);

    Invitation toEntity(InvitationDTO invitationDTO);
}
