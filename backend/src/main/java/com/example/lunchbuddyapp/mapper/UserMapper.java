package com.example.lunchbuddyapp.mapper;

import com.example.lunchbuddyapp.dto.UserDTO;
import com.example.lunchbuddyapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "invitations", ignore = true)
    UserDTO toDto(User user);

    User toEntity(UserDTO userDTO);
}
