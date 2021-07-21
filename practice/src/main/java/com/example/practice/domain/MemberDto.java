package com.example.practice.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDto {
	private String email;
	private String password;
	private String auth;
}
