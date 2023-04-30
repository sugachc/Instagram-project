package com.cos.photosns.web.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photosns.config.auth.PrincipalDetails;
import com.cos.photosns.domain.image.Image;
import com.cos.photosns.service.ImageService;
import com.cos.photosns.service.LikesService;
import com.cos.photosns.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ImageApiController {

		private final ImageService imageService;
		private final LikesService likesService; 
		
		@GetMapping("/api/image")
		public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails,
				@PageableDefault(size=3) Pageable pageable){ //최신데이터가 항상 앞으로 오게
			Page<Image> images=imageService.imageStory(principalDetails.getUser().getId(),pageable);
			return new ResponseEntity<>(new CMRespDto<>(1,"성공",images),HttpStatus.OK);
		}
		
		
		//어떤 이미지를 좋아요했는지
		@PostMapping("/api/image/{imageId}/likes")
		public ResponseEntity<?> likes(@PathVariable int imageId,@AuthenticationPrincipal PrincipalDetails principalDetails){
			
			likesService.likes(imageId,principalDetails.getUser().getId());
			return new ResponseEntity<>(new CMRespDto<>(1,"좋아요성공",null),HttpStatus.CREATED);
		}
		
		
		//좋아요 취소
		@DeleteMapping("/api/image/{imageId}/likes")
		public ResponseEntity<?> unLikes(@PathVariable int imageId,@AuthenticationPrincipal PrincipalDetails principalDetails){
			
			likesService.unlikes(imageId,principalDetails.getUser().getId());
			return new ResponseEntity<>(new CMRespDto<>(1,"좋아요취소성공",null),HttpStatus.CREATED);
		}
	
}
