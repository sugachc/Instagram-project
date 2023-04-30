package com.cos.photosns.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

//어노테이션이 없어도 IoC등록이 자동으로 된다.(JpaRepository를 상송)
public interface UserRepository extends JpaRepository<User,Integer>{

	User findByUserName(String username);
    //JPA query method  유저이름으로 찾아서 UserObject반환
	
}
