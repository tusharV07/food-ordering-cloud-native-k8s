package com.tsv.userinfo.mapper;

import org.mapstruct.Mapper;

import com.tsv.userinfo.dto.UserDTO;
import com.tsv.userinfo.entity.User;

@Mapper(componentModel="spring")
public interface UserMapper {
	User toEntity(UserDTO userDto);
	UserDTO toDto(User user);
}
