package com.cos.photosns.web.dto.auth;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.photosns.domain.user.User;

import lombok.Data;

@Data //Getter,Setter
public class SignupDto {
	
	// https://bamdule.tistory.com/35 (@Valid 어노테이션 종류)
	@Size(min=2,max=20)
	@NotBlank 
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String email;
	@NotBlank
	private String name;
	
	//데이터를 담을때 좋은 방법 
	public User toEntity(){
	   return User.builder()
			   .userName(username)
			   .password(password)
			   .email(email)
			   .name(name)
			   .build();
	}

}
