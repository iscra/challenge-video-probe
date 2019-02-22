package com.example.videoinfo.demo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "infos")
public class VideoInfoController {

	private static final Logger logger = LoggerFactory.getLogger(VideoInfoController.class);

	@Autowired
	VideoInfoRepository videoInfoRepository;

	private boolean allowDuplicateNames = true;

	/**
	 * Create or update video Info
	 * @param info VideoInfo
	 * @return the created info
	 */
	@PostMapping(consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public VideoInfo createInfo(@RequestBody VideoInfo info,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("createInfo: {}", info);
		if (!allowDuplicateNames) {
			List<VideoInfo> found = videoInfoRepository.findByName(info.getName());
			if (found != null && !found.isEmpty() ) {
				// just update the duplicate
				VideoInfo updated  = found.get(0);
				updated.setFormatName(info.getFormatName());
				updated.setDuration(info.getDuration());
				videoInfoRepository.save(updated);
				return updated;
			} 
		}
		VideoInfo createdInfo = videoInfoRepository.save(info);
		response.setHeader("Location", request
				.getRequestURL()
				.append("/")
				.append(createdInfo.getId()).toString());
		logger.debug("Created info {} with id {}",
				createdInfo.getName(), createdInfo.getId());
		return createdInfo;

	}

	@GetMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<VideoInfo> videosByName(
			@RequestParam String name) {
		logger.debug("videosByName {}", name);
		List<VideoInfo> found = videoInfoRepository.findByName(name);
		return found;
	}


}
