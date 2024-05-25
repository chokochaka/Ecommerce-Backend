package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.user.ReturnUserDto;
import com.spring.ecommerce.dto.user.UserRefreshToken;
import com.spring.ecommerce.dto.user.UserRole;
import com.spring.ecommerce.models.RefreshToken;
import com.spring.ecommerce.models.Role;
import com.spring.ecommerce.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "refreshToken", source = "refreshToken", qualifiedByName = "mapRefreshToken")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    @Mapping(target = "createdOn", source = "createdOn")
    @Mapping(target = "lastUpdatedOn", source = "lastUpdatedOn")
    ReturnUserDto userToReturnUserDto(User source);

    @Named("mapRefreshToken")
    default UserRefreshToken map(RefreshToken refreshToken) {
        if (refreshToken == null) {
            return null;
        }
        UserRefreshToken userRefreshToken = new UserRefreshToken();
        userRefreshToken.setUser(refreshToken.getUser().getId());
        userRefreshToken.setRefreshToken(refreshToken.getRefreshToken());
        userRefreshToken.setIssuedAt(refreshToken.getIssuedAt());
        userRefreshToken.setExpiresAt(refreshToken.getExpiresAt());
        userRefreshToken.setId(refreshToken.getId());
        return userRefreshToken;
    }

    @Named("mapRoles")
    default UserRole map(Role role) {
        if (role == null) {
            return null;
        }
        UserRole userRole = new UserRole();
        userRole.setRoleName(role.getRoleName());
        return userRole;
    }
}
