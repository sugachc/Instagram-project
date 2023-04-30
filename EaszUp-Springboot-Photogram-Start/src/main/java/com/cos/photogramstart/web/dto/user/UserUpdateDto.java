package com.cos.photogramstart.web.dto.user;

import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
	
	@NotBlank
	private String name; //필수
	@NotBlank
	private String password; //필수
	private String website;
	private String bio;
	private String phone;
	private String gender;

	//조금 위험함 코드리팩토링 예정
	public User toEntity(){
	   return User.builder()
			   .name(name) //이름을 기재안했으면 문제생김. Validation체크가 필요.
			   .password(password) //패스워드를 기재안했으면 문제생김. Validation체크가 필요
			   .website(website)
			   .bio(bio)
			   .phone(phone)
			   .gender(gender)
			   .build();
	}
	
	
}
