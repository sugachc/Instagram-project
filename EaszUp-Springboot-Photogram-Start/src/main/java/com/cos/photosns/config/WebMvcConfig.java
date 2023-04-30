package com.cos.photosns.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;



@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

		@Value("${file.path}")
		private String uploadFolder;
		
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			
			WebMvcConfigurer.super.addResourceHandlers(registry);
			
			 //file:/// C://webproject/springbootwork/upload/
			registry  
				.addResourceHandler("/upload/**") //jsp페이지에서 /upload/**이런 주소패턴이 나오면 발동
				.addResourceLocations("file:///"+uploadFolder) //위와 같은 경로명이 있으면 이게 발동
				.setCachePeriod(60*10*6)
				.resourceChain(true)
				.addResolver(new PathResourceResolver());
			 
		}
		
	
}
