package com.cos.photosns.handler.ex;

import java.util.Map;

public class CustomApiException extends RuntimeException{

	//객체를 구분할때 시리얼넘버
	private static final long serialVersionUID=1L;
		
	//생성자
	public CustomApiException(String message) {
		//부모한테 메세지던짐
		super(message);
	}
	
	
}
