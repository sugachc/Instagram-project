package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity //해당파일로 시큐리티를 활성화
@Configuration  //IoC 
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private final OAuth2DetailsService oAuth2DetailService;
	
	@Bean
	public BCryptPasswordEncoder encode() {
		//SecurityConfig가 IoC에 등록될때 Bean어노테이션을 읽어서 이걸 리턴해서 IoC가 들고있음
		return new BCryptPasswordEncoder(); 
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//configure오버라이드해서 super삭제-기존 시큐리티가 가지고 있는 기능이 비활성화됨
		//인증이 되지않은 모든 사용자는 로그인페이지로 보내도록
		
		http.csrf().disable(); //비활성화 
			
		http.authorizeRequests()       
			.antMatchers("/","/user/**","image/**,/subscribe/**","/comment/**","/api/**").authenticated()  //해당주소는 인증이 필요하고 아무나 못들어옴
			.anyRequest().permitAll()  //그게 아닌 모든 요청을 허용하겠다.
			.and()
			.formLogin() //form 로그인할건데
			.loginPage("/auth/signin") //GET 니가 주소요청을 하면 이페이지로 보낼것
			.loginProcessingUrl("/auth/signin") //POST->스프링시큐리티가 로그인프로세스 진행
			.defaultSuccessUrl("/") //로그인을 정상적으로 처리하면 /로 가게할것
			.and()
			.oauth2Login() //oauth2로그인도 할것
			.userInfoEndpoint() //최종응답을 회원정보를 바로 받을 수 있음
			.userService(oAuth2DetailService);
	}
	
}
