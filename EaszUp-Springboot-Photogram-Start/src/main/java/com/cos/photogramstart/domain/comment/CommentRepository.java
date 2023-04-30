package com.cos.photogramstart.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface CommentRepository  extends JpaRepository<Comment, Integer>{

	
//오류 native쿼리는 들어간 객체를 리턴받을 수 없고 modifying int가 리턴됨
//		@Modifying
//		@Query(value="INSERT INTO comment(content,imageId,userId,createDate)VALUES(:content,:imageId,:userId,now())",nativeQuery=true)
//		Comment mSave(String content,int imageId,int userId);
	
	
	
}
