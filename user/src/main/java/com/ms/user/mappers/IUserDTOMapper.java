package com.ms.user.mappers;

import com.ms.user.dto.UserDTO;
import com.ms.user.entities.Role;
import com.ms.user.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface IUserDTOMapper {
    @Mapping(target = "roles", source = "roles", qualifiedByName = {"mapRoles"})
    UserDTO userToUserDTO (User user);

    @Named("mapRoles")
    default List<String> mapRoles(Set<Role> roles) {
        return roles.stream().map(Role::getValue).collect(Collectors.toList());
    }

}
