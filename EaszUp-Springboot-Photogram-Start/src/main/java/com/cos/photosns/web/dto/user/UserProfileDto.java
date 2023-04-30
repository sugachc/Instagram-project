package com.cos.photosns.web.dto.user;

import com.cos.photosns.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserProfileDto{
	private boolean pageOwnerState; //이 페이지의 주인인지 아닌지에 대한 데이터
	private int imageCount; //게시물수
	private boolean subscribeState; //구독상태인지
	private int subscribeCount; 
	private User user;
	
}
