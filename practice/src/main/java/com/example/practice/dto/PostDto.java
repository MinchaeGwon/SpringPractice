package com.example.practice.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class PostDto {
	private String title;
	private String content;
	
	@Getter @Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ListResponse {
		private Long postId;
		private String title;
		private String content;
		private LocalDateTime createAt;
	}
	
}
