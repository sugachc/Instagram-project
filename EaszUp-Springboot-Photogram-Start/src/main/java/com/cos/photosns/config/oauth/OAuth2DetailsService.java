package com.cos.photosns.config.oauth;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.photosns.config.auth.PrincipalDetails;
import com.cos.photosns.domain.user.User;
import com.cos.photosns.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service														//oauth2User서비스 형태로 만들어준다
public class OAuth2DetailsService extends DefaultOAuth2UserService{

	private final UserRepository userRepository;
	
	//페이스북 버튼클릭후 로그인해서 페이스북으로 부터 받은 응답을 처리하는 곳
	//회원정보를 파싱해서 넣어줌
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		//System.out.println("OAuth2서비스 탐");
		OAuth2User oauth2User=super.loadUser(userRequest);
		//System.out.println(oauthUser.getAttributes());
		
		Map<String,Object> userInfo=oauth2User.getAttributes();
		
		String userName="facebook_"+(String)userInfo.get("id");
		String password=new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
		String email=(String)userInfo.get("email");
		String name=(String)userInfo.get("name");
		
		User userEntity=userRepository.findByUserName(userName);
		
		if(userEntity==null) { //최초로그인
			User user=User.builder()
					.userName(userName)
					.password(password)
					.email(email)
					.name(name)
					.role("ROLE_USER")
					.build();
			
			return new PrincipalDetails(userRepository.save(user),oauth2User.getAttributes());
			
		}else {//페이스북으로 이미 회원가입 되어있다는 뜻

			//시큐리티 세션에oauth2로 로그인에서 PrincipalDetails로 들어가게됨
			return new PrincipalDetails(userEntity,oauth2User.getAttributes());
		}

	}
	
}
