package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails,OAuth2User{

	private static final long serialVersionUID = 1L;

	//User객체를 담고싶음
	private User user; 
	private Map<String,Object> attributes;
	
	//생성자
	//세션에 저장된 User오브젝트를 활용할 수 있을 것
	public PrincipalDetails(User user) {
		this.user=user;
	}
	
	public PrincipalDetails(User user,Map<String,Object> attributes) {
		this.user=user;
	}

	//권한을 가져오는 함수
	//권한:한개가 아닐 수 있음(3개 이상의 권한)
	@Override            //얘를 상속하는 어떤타입
	public Collection<? extends GrantedAuthority> getAuthorities() { 
		Collection<GrantedAuthority> collector=new ArrayList<>(); 
		collector.add(()-> {return user.getRole();});  //람다
		return collector;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {//만기되지않았니
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { //니 계정이 잠겼니
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { //니 비밀번호가 만료된건아니냐
		return true;
	}

	@Override
	public boolean isEnabled() { //니 계정이 활성화되어있니
		return true;
	}

	//oauth2로 로그인할때는 여기에 저장
	@Override
	public Map<String, Object> getAttributes() {
		return attributes; //{id:222222 name:가나다 email:ganada@nate.com}
	}

	@Override
	public String getName() {
		return (String)attributes.get("name");
	}
		
}
