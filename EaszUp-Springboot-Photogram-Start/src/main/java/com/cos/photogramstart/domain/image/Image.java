package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String caption;	//글내용
	private String postImageUrl; //사진을 전송받아서 그 사진을 서버에 특정폴더에 저장-DB에 그 저장된 경로를 insert
	
	@JsonIgnoreProperties({"images"}) //이미지랑 유저정보까지만 필요(유저정보가 가진 이미지는 무시)
	@JoinColumn(name = "userId") //fk이름지정
	@ManyToOne(fetch = FetchType.EAGER) //내가 이미지를 select하면 EAGER전략이기 때문에 user도 조인해서 가져옴
	private User user; //누가 업로드 했는지
	
	//이미지 좋아요 
	//양방향매핑
	//하나의 이미지에 여러개의 좋아요
	//Lazy 연관관계의 주인이 아님
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy = "image") 
	private List<Likes> likes;

	
	//댓글
	@OrderBy("id DESC") //역순으로 오게
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy="image") //fk에 대한 자바변수를 적어주는 것 //필요할때만 땡겨오는 Lazy전략
	private List<Comment> comments; //comment안의 image가 fk
	
	private LocalDateTime createDate;
	
	@Transient //DB에 컬럼안만들어짐
	private boolean likeState;
	
	
	@Transient
	private int likeCount;
	
	@PrePersist
	public void createDate() {
		this.createDate=LocalDateTime.now();
	}

	//오브젝트를 콘솔에 출력할때 문제가 될 수 있어서 User부분을 출력되지 않게함
//	@Override
//	public String toString() {
//		return "Image [id=" + id + ", caption=" + caption + ", postImageUrl=" + postImageUrl + ", createDate="
//				+ createDate + "]";
//	}
	

}
