package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

		private final ImageRepository imageRepository;


		@Transactional(readOnly=true)
		public List<Image> popularPhoto() {
			
			return imageRepository.mPopular();
		} 
		
		
		@Transactional(readOnly=true) //읽기만함 영속성컨텍스트 변경감지를 해서,더티체킹,flush(반영)x
		public Page<Image> imageStory(int principalId,Pageable pageable){
			Page<Image> images=imageRepository.mStory(principalId,pageable);
			
			//image에 좋아요 상태담기
			//이미지 하나가 들고있는 like정보를 가져옴
			//2번으로 로그인했을때 
			
			images.forEach((image)->{
				
				    //이미지좋아요수 가져감
					image.setLikeCount(image.getLikes().size());	
				
					image.getLikes().forEach((like)->{
						if(like.getUser().getId()==principalId) { //해당 이미ㅣ지에 좋아요한 사람들을 찾아서 현재 로그인한 사람이 좋아요한것긴지 비교
							 image.setLikeState(true);
						}
						
					});
					
			});
			
			
			return images;
		}

		@Value("${file.path}")  //aplication yml에 지정된 경로
		private String uploadFolder;
		
		//사진업로드
		@Transactional
	    public void ImageUpload(ImageUploadDto imageUploadDto,PrincipalDetails principalDetails){
	    	
	    	UUID uuid=UUID.randomUUID(); //uuid
	    	String imageFileName=uuid+"_"+imageUploadDto.getFile().getOriginalFilename(); //실제파일명 1.jpg
	    	System.out.println("이미지 파일이름"+imageFileName);
	    	
	    	//java NIO
	    	Path imageFilePath=Paths.get(uploadFolder+imageFileName);
	    	
	    		                //통신,I/O가 일어날때->예외가 발생할 수 있다.
	    	try {
                                //파일경로,실제이미지파일바이트화	    		
	    		Files.write(imageFilePath,imageUploadDto.getFile().getBytes());
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	
	    	//image테이블에 저장 
	    	Image image=imageUploadDto.toEntity(imageFileName,principalDetails.getUser()); //UUID.jpg
	    	imageRepository.save(image);
	    	
	    }
	
}
