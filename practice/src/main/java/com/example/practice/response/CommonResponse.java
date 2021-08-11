package com.example.practice.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommonResponse<T> {
	private String message;
	private T result;
	
	public CommonResponse(T result) {
		this.message = "success";
		this.result = result;
	}

}
