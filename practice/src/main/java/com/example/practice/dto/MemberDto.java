package com.example.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class MemberDto {
	private String email;
	private String password;
	private String auth;
	
	@Getter @Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Profile {
		private Long memberId;
		private String email;
	}
}
