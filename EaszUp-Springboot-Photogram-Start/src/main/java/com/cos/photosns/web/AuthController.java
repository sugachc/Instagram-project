package com.cos.photosns.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.photosns.domain.user.User;
import com.cos.photosns.handler.ex.CustomValidationException;
import com.cos.photosns.service.AuthService;
import com.cos.photosns.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //fianl필드 DI할때 사용
@Controller //1. IoC 2.파일을 리턴하는 컨트롤러 
public class AuthController {

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
		
//	public AuthController(AuthService authService) {
//		this.authService=authService; //의존성주입
//	}
	
	//자바에서 전역변수에 final이 걸려있으면 무조건 생성자가 실행됨
	private final AuthService authService;
	
	@GetMapping("/auth/signin")
	public String signInForm() {//로그인폼 이동
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signUpForm() { //회원가입폼 이동
		return "auth/signup";
	}
	
	//회원가입버튼->/auth/signup->/auth/signin
	@PostMapping("/auth/signup")                             
	public  String signup(@Valid SignupDto signupDto,BindingResult bindingResult) { //key=value(x-www-form-urlencoded)
		
			//SignupDto 4개의 값-> User라는 Object에 집어넣을 것
			User user=signupDto.toEntity(); 
			User userEntity=authService.signUp(user);
			//log.info(user.toString());
			//로그남기는 후처리
			return "auth/signin";
	
	}	
	
}
