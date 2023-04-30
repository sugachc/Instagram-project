package com.cos.photosns.domain.comment;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photosns.domain.image.Image;
import com.cos.photosns.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private int id;
		
		
		@Column(length =100,nullable=false) //제약조건
		private String content;
		
		//한명이 유저는 여러개 쓸 수 있음
		//내가 댓글을 select할때 user,image
		@JsonIgnoreProperties({"images"})
		@JoinColumn(name="userId")
		@ManyToOne(fetch=FetchType.EAGER) 
		private User user;
		
		//하나의 이미지는 댓글이 여러개
		@JoinColumn(name="imageId")
		@ManyToOne(fetch=FetchType.EAGER) 
		private Image image;
		
		private LocalDateTime createDate;
		
		@PrePersist
		public void createDate() {
			this.createDate=LocalDateTime.now();
		}
		
	
}
