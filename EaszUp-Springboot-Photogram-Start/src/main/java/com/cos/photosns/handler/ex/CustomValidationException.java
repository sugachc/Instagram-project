package com.cos.photosns.handler.ex;

import java.util.Map;

public class CustomValidationException extends RuntimeException{

	//객체를 구분할때 시리얼넘버
	private static final long serialVersionUID=1L;
	
	private Map<String,String>errorMap;
	
	//생성자
	public CustomValidationException(String message,Map<String,String> errorMap) {
		//부모한테 메세지던짐
		super(message);
		this.errorMap=errorMap;
	}
	
	//errorMap을 리턴해주는 getter
	public Map<String,String> getErrorMap(){
		return errorMap;
	}	
	
}
