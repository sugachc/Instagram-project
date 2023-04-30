package com.cos.photosns.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photosns.handler.ex.CustomApiException;
import com.cos.photosns.handler.ex.CustomException;
import com.cos.photosns.handler.ex.CustomValidationApiException;
import com.cos.photosns.handler.ex.CustomValidationException;
import com.cos.photosns.util.Script;
import com.cos.photosns.web.dto.CMRespDto;

@RestController
@ControllerAdvice
public class ControllerexceptionHandler {
	
	@ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {
		
		//CMRespDto,Script비교
		//1.클라이언트에게 응답할때는 Script좋음
		//2.Ajax통신-CMRespDto
		//3.Android통신-CMRespDto
		
		//메시지리턴
    	System.out.println("나 발동되냐");
    	
    	if(e.getErrorMap()==null) {
    		return Script.back(e.getMessage());
    	}else {
    		return Script.back(e.getErrorMap().toString());	
    	}
		
	}
	
	@ExceptionHandler(CustomException.class)
    public String exception(CustomException e) {
		return Script.back(e.getMessage());		
	}
	
	@ExceptionHandler(CustomValidationApiException.class)
	public ResponseEntity<?> validationApiException(CustomValidationApiException e){
		System.out.println("=================나 실행됨=======================");
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),e.getErrorMap()),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomApiException.class)
	public ResponseEntity<?> apiException(CustomApiException e){
		System.out.println("=================나 실행됨=======================");
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),null),HttpStatus.BAD_REQUEST);
	}
		
	
//무엇을 리턴할지 모를때 제네릭타입
//	public CMRespDto<?> validationException(CustomValidationException e) {
//		
//		return new CMRespDto<Map<String,String>>(-1,e.getMessage(),e.getErrorMap());
//	}

}
