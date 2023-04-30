package com.cos.photogramstart.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;

//회원가입 로그인 전처리 후처리 공통기능

@Component  //RestController,Service모든것들이 Component로 상속해서 만들어져있음 
@Aspect
public class ValidationAdvice {

	                             //어떤 함수를 선택할래 *은 전부한다는것
	                             //web패키지의 컨트롤러로 끝나는 모든 메서드(파라미터상관x)
	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
	 public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
		
		  System.out.println("web api 컨트롤러");
		 //proceedingJoinPoin=> profile함수의 모든 곳에 접근할 수 있는 변수
		 //profile함수보다 먼저 실행
		  
		  //함수의 매개변수에 접근해서 뽑아보는 것
		  Object[] args = proceedingJoinPoint.getArgs();
			for (Object arg : args) {
				if (arg instanceof BindingResult) {
					BindingResult bindingResult = (BindingResult) arg;

					if (bindingResult.hasErrors()) {
						Map<String, String> errorMap = new HashMap<>();

						for (FieldError error : bindingResult.getFieldErrors()) {
							errorMap.put(error.getField(), error.getDefaultMessage());
						}
						throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
					}

				}
			}
		 
		//그 함수로 다시 돌아가라는 명령
		 return proceedingJoinPoint.proceed(); //profile함수가 실행됨
	 } 
	
	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
	 public Object advice(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
		 
			//System.out.println("web 컨트롤러 ==========================");
			Object[] args = proceedingJoinPoint.getArgs();
			for (Object arg : args) {
				if (arg instanceof BindingResult) {
					BindingResult bindingResult = (BindingResult) arg;
					if (bindingResult.hasErrors()) {
						Map<String, String> errorMap = new HashMap<>();
	
						for (FieldError error : bindingResult.getFieldErrors()) {
							errorMap.put(error.getField(), error.getDefaultMessage());
						}
						throw new CustomValidationException("유효성 검사 실패함", errorMap);
					}
				}
			}
			return proceedingJoinPoint.proceed();
	 } 
	 	 
	 //ProceedingJoinPoint commentSave에 모든 접근할 수 있는 모든 정보
	 
	
		//전처리		
	//	유효성 하나라도 실패하면 bidingResult에 담기고 내가 만든 Map에 에러를 담고 
	//	throw해서 exception을 강제로 발동시켜서 데이터를 던진다. 
	//	Exception이 발동하면 ExceptionHandler가 낚아채서 e.getErrorMap()을 리턴하고 종료
		
			
}
