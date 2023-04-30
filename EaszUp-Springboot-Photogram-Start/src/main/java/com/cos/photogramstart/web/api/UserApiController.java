package com.cos.photogramstart.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController //데이터를 응답할 것
public class UserApiController {

	private final UserService userService;
	private final SubscribeService subscribeService;
	
	
	@PutMapping("/api/user/{principalId}/profileImageUrl")
	public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principalId,MultipartFile profileImageFile,
			@AuthenticationPrincipal PrincipalDetails principalDetails){
		
			User userEntity = userService.changeProfile(principalId, profileImageFile);
			principalDetails.setUser(userEntity); // 세션 변경
			return new ResponseEntity<>(new CMRespDto<>(1, "프로필사진변경 성공", null), HttpStatus.OK);
	}
	
	                                         //내가 이동한 페이지의 주인이 구독하고 있는 정보
	@GetMapping("/api/user/{pageUserId}/subscribe")
	public ResponseEntity<?> subscribeList(@PathVariable int pageUserId,@AuthenticationPrincipal PrincipalDetails principalDetails){
		
		List<SubscribeDto> subscribeDto=subscribeService.subList(principalDetails.getUser().getId(),pageUserId);
		
		return new ResponseEntity<>(new CMRespDto<>(1,"구독자정보리스트 불러오기 성공",subscribeDto),HttpStatus.OK);
	}
	
	
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(
			@PathVariable int id,
			@Valid UserUpdateDto userUpdateDto,
			BindingResult bindingResult, //위치가 중요 @Valid가 적혀있는 다음 파라미터에 적어야한다.
			@AuthenticationPrincipal PrincipalDetails principalDetails ) {	
				
//		유효성 하나라도 실패하면 bidingResult에 담기고 내가 만든 Map에 에러를 담고 
//		throw해서 exception을 강제로 발동시켜서 데이터를 던진다. 
//		Exception이 발동하면 ExceptionHandler가 낚아채서 e.getErrorMap()을 리턴하고 종료
//		if(bindingResult.hasErrors()) {
//			Map<String,String> errorMap=new HashMap<>();
//			
//			for(FieldError error:bindingResult.getFieldErrors()) {
//				    errorMap.put(error.getField(), error.getDefaultMessage());
////				    System.out.println("==================="); 
////					System.out.println(error.getDefaultMessage());
////					System.out.println("==================="); 
//			}
//			throw new CustomValidationApiException("유효성검사 실패함",errorMap);
//		}
		//System.out.println(userUpdateDto);
		User userEntity=userService.회원수정(id,userUpdateDto.toEntity());
		principalDetails.setUser(userEntity);
		return new CMRespDto<>(1,"회원수정완료",userEntity);
	}
	
	
}
