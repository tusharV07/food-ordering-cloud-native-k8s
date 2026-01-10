package com.tsv.userinfo.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsv.userinfo.dto.UserDTO;
import com.tsv.userinfo.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:4200")
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("/addUser")
	public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDto){
		UserDTO savedUser=userService.saveUser(userDto);
		URI userUri=URI.create("/api/v1/user/fetchUserById/"+savedUser.getId());
		return ResponseEntity.created(userUri).body(savedUser);
	}
	
	@GetMapping("/fetchUserById/{userId}")
	public ResponseEntity<UserDTO> fetchUserById(@PathVariable Integer userId){
		Optional<UserDTO> fetchedUser=userService.getUserById(userId);
		if(fetchedUser.isPresent()) {
			return ResponseEntity.ok(fetchedUser.get());
		}
		return ResponseEntity.notFound().build();
	}
}
