package com.example.videoinfo.demo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface VideoInfoRepository extends CrudRepository<VideoInfo, Long> { 

	  List<VideoInfo> findByName(String name);

}
