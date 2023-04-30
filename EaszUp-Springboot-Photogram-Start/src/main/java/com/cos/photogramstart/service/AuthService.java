package com.cos.photogramstart.service;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service //1. IoC 2.트랜잭션 처리
public class AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional //Write(Insert,Update,Delete)
	public User signUp(User user) { //외부에서 통신을 통해 받은 것을 User오브젝트에 받은 것 
		//회원가입 진행
		String rawPassword=user.getPassword();
		String encPassword=bCryptPasswordEncoder.encode(rawPassword); //암호화패스워드
		user.setPassword(encPassword); 
		user.setRole("ROLE_USER"); //관리자는  ROLE_ADMIN
		
		//데이터베이스의 데이터를 User오브젝트에 받은 것
		User userEntity=userRepository.save(user); //save가 된 뒤에 잘들어갔다고 응답을 받음 
		return userEntity;
	}
}
