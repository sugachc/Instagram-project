package com.cos.photogramstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {


	   //insert,delete처럼DB에 영향주는 것은 transactional 애노테이션
		private final SubscribeRepository subscribeRepository;
	
		private final EntityManager em;//Repository는 EntityManager를 구현해서 만들어져 있는 구현체
	  
		@Transactional(readOnly=true) //구독리스트 정보 불러오기
		public List<SubscribeDto> subList(int principalId, int pageUserId) {
			
			//쿼리준비
			StringBuffer sb=new StringBuffer();
			sb.append("SELECT u.id,u.username,u.profileImageUrl, ");
			sb.append("if((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId= u.id),1,0) subscribeState, ");
			sb.append("if((?=u.id),1,0) equalUserState ");
			sb.append("FROM user u INNER JOIN subscribe s ");
			sb.append("ON u.id = s.toUserId ");
			sb.append("WHERE s.fromUserId=? "); //세미콜론 첨부하면 안됨 fromUserId는 페이지의 주인아이디
			
			//1.물음표 로그인아이디 principalId
			//2.물음표는 로그인아이디 principal	
			//3.마지막 물음표는 페이지의 주인 pageUserId
			
			
			//쿼리완성
			 Query query=em.createNativeQuery(sb.toString())
					 .setParameter(1,principalId)
					 .setParameter(2,principalId)
					 .setParameter(3,pageUserId);
			
			 //쿼리실행
			 //qlrm 데이터베이스에서 result된 결과를 자바클래스에 매핑해주는 라이브러리(Dto에 DB결과 매핑)
			 //내가 리턴받을 결과가 model이 아닌 새로운 조합의 데이터이면 JpaRepository를 못쓰기 때문에 native쿼리를 실행해야됨
			 JpaResultMapper result=new JpaResultMapper();
			 List<SubscribeDto> subscribeDtos=result.list(query, SubscribeDto.class);
			 
			 return subscribeDtos;
		}
	
	    @Transactional 
		public void mSubscribe(int fromUserId,int toUserId){
	    	 
		    	try {
		    		   subscribeRepository.mSubscribe(fromUserId, toUserId);
				} catch (Exception e) {
					   throw new CustomApiException("이미 구독을 하였습니다.");
				}
			   
		}
		
	    @Transactional
		public void mUnSubscribe(int fromUserId,int toUserId) {
			  subscribeRepository.mUnSubscribe(fromUserId, toUserId);
		}

}
