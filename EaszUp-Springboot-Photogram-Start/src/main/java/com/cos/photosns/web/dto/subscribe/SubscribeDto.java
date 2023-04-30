package com.cos.photosns.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//구독정보를 보는 Dto
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscribeDto {

	private int id; //로그인을 a로 했는데 구독리스트에 상대id에 대한 정보
	private String username; //상대이름에 대한 정보
	private String profileImageUrl; 
	private Integer subscribeState; //구독한 상태인지 안한상태인지
	private Integer equalUserState; //로그인한 사용자와 동일인인지 아닌디(동일인이면 구독버튼 안보이게)
	
}
