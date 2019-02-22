package com.example.videoprobe.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InfoService {

	private static final Logger logger = LoggerFactory.getLogger(InfoService.class);

	private RestTemplate restTemplate;

	private String postUrl = "http://localhost:8081/infos";
	
	public InfoService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public void updateVideoInfoService(VideoInfo videoInfo) {
		HttpEntity<VideoInfo> request = new HttpEntity<>(videoInfo);
		ResponseEntity<VideoInfo> response = restTemplate.postForEntity(postUrl, request, VideoInfo.class);
		logger.info("VideoInfo update response: {}", response.toString() );		
	}

}
