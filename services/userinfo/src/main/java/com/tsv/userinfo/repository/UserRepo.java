package com.tsv.userinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsv.userinfo.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
