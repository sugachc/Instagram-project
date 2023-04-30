package com.cos.photosns.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photosns.domain.subscribe.SubscribeRepository;
import com.cos.photosns.domain.user.User;
import com.cos.photosns.domain.user.UserRepository;
import com.cos.photosns.handler.ex.CustomApiException;
import com.cos.photosns.handler.ex.CustomException;
import com.cos.photosns.handler.ex.CustomValidationApiException;
import com.cos.photosns.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

		private final UserRepository  userRepository;
		private final SubscribeRepository subscribeRepository;
		private final BCryptPasswordEncoder bCrptPasswordEncoder; //암호화
		
		@Value("${file.path}")  //aplication yml에 지정된 경로
		private String uploadFolder;
		
		@Transactional
		public User changeProfile(int principalId, MultipartFile profileImageFile) {
			
				UUID uuid=UUID.randomUUID(); //uuid
		    	String imageFileName=uuid+"_"+profileImageFile.getOriginalFilename(); //실제파일명 1.jpg
		    	System.out.println("이미지 파일이름"+imageFileName);
		    	
		    	//java NIO
		    	Path imageFilePath=Paths.get(uploadFolder+imageFileName);
		    	
		    		                //통신,I/O가 일어날때->예외가 발생할 수 있다.
		    	try {
		                            //파일경로,실제이미지파일바이트화	    		
		    		Files.write(imageFilePath,profileImageFile.getBytes());
		    	}catch(Exception e){
		    		e.printStackTrace();
		    	}
		    	
		    	User userEntity=userRepository.findById(principalId).orElseThrow(()->{
		    		throw new CustomApiException("유저를 찾을 수 없습니다.");
		    	});
		    	
		    	userEntity.setProfileImageUrl(imageFileName);
	    	
		    	return userEntity;
		}
			
		
		@Transactional(readOnly=true)
		public UserProfileDto userProfile(int pageUserId,int principalId){
			//해당페이지의 주인 아이디의 사진이 나와야한다
			//SELECT * FROM image WHERE userId=:userId;
			
					UserProfileDto dto=new UserProfileDto();
			
					User userEntity=userRepository.findById(pageUserId).orElseThrow(()->{ //아이디가 없으면 exception을 탐
					  	throw new CustomException("해당 프로필 페이지는 없는 페이지입니다");
					});
					
					dto.setUser(userEntity);
					dto.setImageCount(userEntity.getImages().size());
					dto.setPageOwnerState(pageUserId==principalId); 
					
					//구독상태
					int subscribeState=subscribeRepository.mSubscribeState(principalId, pageUserId);
					int subscirbeCount=subscribeRepository.mSubscribeCount(pageUserId);
					
					dto.setSubscribeState(subscribeState==1);
					dto.setSubscribeCount(subscirbeCount);
					
					//좋아요카운트
					userEntity.getImages().forEach((image)->{
						image.setLikeCount(image.getLikes().size());
					});
					
					return dto;
			}
		
		@Transactional 
		public User 회원수정(int id,User user) {
			
			//1.영속화
			//Optional이란? (1) 무조건 찾았다 걱정마 get() (2) 못찾으면 익셉션 발동시킴 orElseThrow()     
			User userEntity=userRepository.findById(id).orElseThrow(()-> {
			//new Supplier<IllegalArgumentException>
			return new CustomValidationApiException("찾을 수 없는 id입니다.");}); 
			
			//2.영속화된 오브젝트를 수정-더티체킹(업데이트 완료)
			userEntity.setName(user.getName());
			
			String rawPassword=user.getPassword();
			String encPassword=bCrptPasswordEncoder.encode(rawPassword);
			
			userEntity.setPassword(encPassword);
			userEntity.setBio(user.getBio());
			userEntity.setWebsite(user.getWebsite());
			userEntity.setPhone(user.getPhone());
			userEntity.setGender(user.getGender());
			
			return userEntity;
		}
		
	
}
