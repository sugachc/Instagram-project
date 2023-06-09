package com.cos.photosns.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photosns.config.auth.PrincipalDetails;
import com.cos.photosns.service.SubscribeService;
import com.cos.photosns.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class SubscribeApiController {

	private final SubscribeService subscribeService;
	
	@PostMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> subscribe(@AuthenticationPrincipal  PrincipalDetails principalDetails,
			           @PathVariable int toUserId ){ //누가누구를 구독했는지 로그인한 사람
		
			subscribeService.mSubscribe(principalDetails.getUser().getId(),toUserId);
		return new ResponseEntity<>(new CMRespDto<>(1,"구독하기 성공",null),HttpStatus.OK);
	}
	
	@DeleteMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> unSubscribe(@AuthenticationPrincipal  PrincipalDetails principalDetails,
	           @PathVariable int toUserId){
		      subscribeService.mUnSubscribe(principalDetails.getUser().getId(), toUserId);
		return new ResponseEntity<>(new CMRespDto<>(1,"구독취소하기 성공",null),HttpStatus.OK);
	}
	
}
