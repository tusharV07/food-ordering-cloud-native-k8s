package com.tsv.userinfo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@EqualsAndHashCode.Exclude
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private Integer id;
	private String userName;
	private String userPassword;
	private String address;
	private String city;

}
