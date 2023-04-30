package com.cos.photosns.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photosns.domain.comment.Comment;
import com.cos.photosns.domain.comment.CommentRepository;
import com.cos.photosns.domain.image.Image;
import com.cos.photosns.domain.user.User;
import com.cos.photosns.domain.user.UserRepository;
import com.cos.photosns.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	
		private final CommentRepository commentRepository;
		private final UserRepository userRepository;
		
		@Transactional
		public Comment commentWrite(String content,int imageId, int userId) {
			
			//Tip 객체를 만들때 id값만 담아서 insert할 수 있다
			Image image=new Image();
			image.setId(imageId);
			
			User userEntity=userRepository.findById(userId).orElseThrow(()->{
				throw new CustomApiException("유저 아이디를 찾을 수 없습니다.");
			});
			
			
			Comment comment=new Comment();
			comment.setContent(content);
			comment.setImage(image);
			comment.setUser(userEntity);
			
			return commentRepository.save(comment);
		} 
		
		//댓글삭제
		@Transactional
		public void  deleteComment(int id) {
			 try {
				 commentRepository.deleteById(id);
			 }catch(Exception e) {
				 throw new CustomApiException(e.getMessage());
				 //customException html파일을 리턴
				 //customApiException은 데이터파일을 리턴
				 //customValidationException 처음에 값을 받을때 처리
			 }
			
		}
	
}
