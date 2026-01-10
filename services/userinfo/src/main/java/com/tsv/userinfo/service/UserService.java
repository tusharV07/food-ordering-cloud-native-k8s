package com.tsv.userinfo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tsv.userinfo.dto.UserDTO;
import com.tsv.userinfo.entity.User;
import com.tsv.userinfo.mapper.UserMapper;
import com.tsv.userinfo.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepo userRepo;
	private final UserMapper userMapper;

	public UserDTO saveUser(UserDTO userDto) {
		User savedUser = userRepo.save(userMapper.toEntity(userDto));
		return userMapper.toDto(savedUser);
	}
	
	public Optional<UserDTO> getUserById(Integer userId) {
		Optional<User> fetchedUser=userRepo.findById(userId);
		return fetchedUser.map(u->userMapper.toDto(u));
		
	}
}
