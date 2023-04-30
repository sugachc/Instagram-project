package com.cos.photogramstart.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import lombok.Data;


@Data
public class ImageUploadDto {
	//file과 caption을 받음
	private MultipartFile file;
	private String caption;
																				
	public Image toEntity(String postImageUrl,User user){
		return Image.builder()
				.caption(caption)
				.postImageUrl(postImageUrl)
				.user(user)
				.build();
	}
	
}
