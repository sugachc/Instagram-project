package com.cos.photosns.web;



import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photosns.config.auth.PrincipalDetails;
import com.cos.photosns.domain.user.User;
import com.cos.photosns.service.UserService;
import com.cos.photosns.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
	
	@GetMapping("/user/{pageUserId}")  //페이지로 가는 유저아이디                                            //로그인사용자 아이디 넘기기위함
	public String profile(@PathVariable int pageUserId,Model model,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		UserProfileDto dto=userService.userProfile(pageUserId,principalDetails.getUser().getId());
		//현재페이지의 주인일 경우에만 사진등록 버튼을 올리기,아닐경우 구독하기 버튼
		model.addAttribute("dto", dto);
		return "user/profile";
	}
	
	//user몇번을 업데이트 할 것인지
	@GetMapping("/user/{id}/update")
	 public String update(@PathVariable int id,@AuthenticationPrincipal PrincipalDetails principalDetails ) {
		//추천코드
		//세션정보
		//System.out.println("세션정보"+principalDetails.getUser());
		
//비추천코드		
//		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
//		PrincipalDetails mPrincipalDetails=(PrincipalDetails)auth.getPrincipal();
//		System.out.println("직접찾은 세션정보"+mPrincipalDetails.getUser());
//		model.addAttribute("principal",principalDetails.getUser());		
		return "user/update";
	}
	
}
