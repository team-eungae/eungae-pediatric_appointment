package com.playdata.eungae;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class initRedis {
	private final RedisTemplate<String,String> redisTemplate;
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddlAutoCheck;

	@PostConstruct
	public void clearRedis(){
		if(ddlAutoCheck.equals("create")){
			redisTemplate.delete("hospital");
		}
	}

}
