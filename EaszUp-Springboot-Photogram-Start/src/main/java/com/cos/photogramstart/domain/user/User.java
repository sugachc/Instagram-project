package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//JPA-Java Persistence API(자바로 데이터를 영구적으로 저장(DB)할 수 있는 API를 제공)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data 
@Entity //디비에 테이블을 생성
public class User {

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY) 
	//번호증가 전략이 데이터베이스를 따라간다.(오라클-시퀀스/mysql- auto_increment)
	private int id;	
	
	@Column(length=100,unique=true) //길이제약조건,uk제약조건
	private String userName;
	
	@Column(nullable=false) //null 불가능
	private String password;
	
	@Column(nullable=false)
	private String name;
	private String website; //웹사이트
	private String bio; //자기소개
	@Column(nullable=false)
	private String email;
	private String phone;
	private String gender;
	
	private String profileImageUrl;
	private String role; //권한 
	
	//나는 연관관계의 주인이 아니다 그러므로 테이블에 컬럼을 만들면x
	//User를 select할때 해당 Userid로 등록된 image들을 다 가져와
	//Lazy=User를 select할때 해당 Userid로 등록된 image들을 가져오지마-대신 getImages()함수의 image들이 호출될때 가져와
	//Eager=User를 select할때 해당 Userid로 등록된 image들을 전부 Join해서 가져와!
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY) 
	@JsonIgnoreProperties({"user"})
	private List<Image> images;//양방향매핑
	
	private LocalDateTime createDate; //이 데이터가 언제 들어왔는지
	
	@PrePersist //디비에 INSERT되기 직전에 실행
	public void createDate() {
		this.createDate=LocalDateTime.now(); 
	}

	//ValidationAdvice에서
	//객체 부를때images가 출력되면 무한참조가 뜨기때문에 toString images만 빼줌
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", name=" + name + ", website="
				+ website + ", bio=" + bio + ", email=" + email + ", phone=" + phone + ", gender=" + gender
				+ ", profileImageUrl=" + profileImageUrl + ", role=" + role + ", createDate="
				+ createDate + "]";
	}
	
	
	
}
