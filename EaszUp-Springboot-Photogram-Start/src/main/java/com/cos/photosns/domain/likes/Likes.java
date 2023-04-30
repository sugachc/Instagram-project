package com.cos.photosns.domain.likes;

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

import com.cos.photosns.domain.image.Image;
import com.cos.photosns.domain.user.User;
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
@Table(    //a유저가 1번 이미지를 좋아요하면 같은 이미지를 또다시 좋아요 할수없기 때문에 중복이 될수없도록 유니크제약걸음
		uniqueConstraints= {
			@UniqueConstraint(
					name="likes_uk",
					columnNames= {"imageId","userId"}
			)	
		}
	)

public class Likes {//N
	
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private int id;
		 
		//무한참조
		//어떤 이미지를 누가 좋아했는지
		//하나의 이미지는 여러번의 좋아요가 있을 수 있다.  이미지와 좋아요 1:N
		@JoinColumn(name="imageId") //fk 
		@ManyToOne
		private Image image; //1

		@JsonIgnoreProperties({"images"})
		@JoinColumn(name = "userId")
		@ManyToOne
		private User user; // 1
		
		private LocalDateTime createDate;
		
		@PrePersist
		public void createDate() {
			this.createDate=LocalDateTime.now();
		}
	
}
