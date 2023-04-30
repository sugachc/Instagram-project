package com.cos.photosns.domain.subscribe;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photosns.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data 
@Entity 
@Table(  //2개를 복합적으로 Unique제약조건 걸어야할때 =
	uniqueConstraints= {
			@UniqueConstraint(
                name="subscribe_uk",
                columnNames= {"fromUserId","toUserId"} //실제데이터베이스테이블의 컬럼명
			)
	}
)
//구독테이블
public class Subscribe {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="fromUserId") //언더바방식표기를 카멜표기법으로
	private User fromUser; //구독하는 유저
	
	@ManyToOne
	@JoinColumn(name="toUserId")
	private User toUser; //구독받는 유저
	
	private LocalDateTime createDate;
	 
    @PrePersist //디비에 INSERT되기 직전에 실행
    public void createDate() {
    	this.createDate=LocalDateTime.now();
    }
	
}
