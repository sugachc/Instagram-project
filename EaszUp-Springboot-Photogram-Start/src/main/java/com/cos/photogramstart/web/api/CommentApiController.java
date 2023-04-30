package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.CommentService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.comment.CommentDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

	
		private final CommentService commentService;
		
		@PostMapping("/api/comment")                 //Json형식 데이터로 받는 어노테이션
		public ResponseEntity<?> commentSave(@Valid @RequestBody CommentDto commentDto,BindingResult bindingResult,@AuthenticationPrincipal PrincipalDetails principalDetails){
			
//			유효성 하나라도 실패하면 bidingResult에 담기고 내가 만든 Map에 에러를 담고 
//			throw해서 exception을 강제로 발동시켜서 데이터를 던진다. 
//			Exception이 발동하면 ExceptionHandler가 낚아채서 e.getErrorMap()을 리턴하고 종료
//			if(bindingResult.hasErrors()) {
//				Map<String,String> errorMap=new HashMap<>();
//				
//				for(FieldError error:bindingResult.getFieldErrors()) {
//					    errorMap.put(error.getField(), error.getDefaultMessage());
//				}
//				throw new CustomValidationApiException("유효성검사 실패함",errorMap);
//			}
			
			Comment comment=commentService.commentWrite(commentDto.getContent(),commentDto.getImageId(),principalDetails.getUser().getId()); //content,imageId,userId
			
			return new ResponseEntity<>(new CMRespDto<>(1,"댓글쓰기성공",comment),HttpStatus.CREATED);
		}
		
		@DeleteMapping("/api/comment/{id}")
		public ResponseEntity<?> commentDelete(@PathVariable int id){
			commentService.deleteComment(id);
			return new ResponseEntity<>(new CMRespDto<>(1,"댓글삭제성공",null),HttpStatus.OK);
		}
	
}
