package com.cos.photogramstart.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class imageController {
	
	private final ImageService imageService;
 
	@GetMapping({"/","/image/story"})
	public String story() {
		return "image/story";
	}
	
	//인기페이지로 감
	@GetMapping("/image/popular")
	public String popular(Model model) {
		
		List<Image> images=imageService.popularPhoto();
	    model.addAttribute("images", images);		
		
		return "image/popular";
	}
	
	@GetMapping("/image/upload")
	public String upload() {
		return "image/upload";
	}
	
	@PostMapping("/image")                                                                                        //현재로그인한 사용자
	public String imageUpload(ImageUploadDto imageUploadDto,@AuthenticationPrincipal PrincipalDetails principalDetails) { 
		 
		if(imageUploadDto.getFile().isEmpty()) {
			throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
		}
		
		//컨트롤러는 사용자로부터 데이터를 받고 서비스호출하기만 하면됨 
		imageService.ImageUpload(imageUploadDto, principalDetails);
		return "redirect:/user/"+principalDetails.getUser().getId();
	}
	
	
}
