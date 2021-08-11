package com.example.practice.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommonListResponse<T> {
	private String message;
	private int count;
	private T result;
	
	public CommonListResponse(int count, T result) {
		this.message = "success";
		this.count = count;
		this.result = result;
	}

}