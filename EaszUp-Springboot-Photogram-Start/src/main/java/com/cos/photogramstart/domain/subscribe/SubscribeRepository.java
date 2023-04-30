package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe,Integer>{
	
		@Modifying //INSERT,DELETE,UPDATE   DB에 변경을 주는 쿼리에는 이 애노테이션이 있어야됨
		@Query(value="INSERT INTO subscribe(fromUserId,toUserId,createDate)VALUES(:fromUserId,:toUserId,now())",nativeQuery=true)
		 void mSubscribe(int fromUserId,int toUserId); //1(변경된 행의 개수가 리턴됨),실패하면 -1이 리턴
		
		//:은 변수를 바인딩해서 넣겠다는 것
		@Modifying
		@Query(value="DELETE  FROM subscribe WHERE fromUserId=:fromUserId AND toUserId=:toUserId",nativeQuery=true)
		 void mUnSubscribe(int fromUserId,int toUserId); //1,-1
		
		
		//SELECT만 하기때문에 Modifying은 필요없음
		@Query(value="SELECT COUNT(*) FROM subscribe WHERE fromUserId=:principallId AND toUserId=:pageUserId",nativeQuery=true)
		int mSubscribeState(int principallId, int pageUserId);
		
		@Query(value="SELECT COUNT(*) FROM subscribe WHERE fromUserId=:pageUserId",nativeQuery=true)
		int mSubscribeCount(int pageUserId);
	
	
}
