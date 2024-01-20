package com.playdata.eungae.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringConfig implements WebMvcConfigurer {

	@Value("${file.upload.path}")
	String photoAdd;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		// /images/로 시작되는 요청
		String webPath = "/images/**";

		// 실제로 자원이 저장된 로컬 경로
		// String resourcePath ="file:///" + photoAdd;
		String resourcePath = photoAdd;

		// /images/로 시작하는 요청이 오면, C:/upload/ 와 연결
		registry.addResourceHandler(webPath).addResourceLocations(resourcePath);
	}
}