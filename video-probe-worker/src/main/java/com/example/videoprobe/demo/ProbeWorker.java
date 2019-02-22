package com.example.videoprobe.demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zeroturnaround.exec.InvalidExitValueException;
import org.zeroturnaround.exec.ProcessExecutor;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ProbeWorker {

	private static final Logger logger = LoggerFactory.getLogger(ProbeWorker.class);

	@Value( "${probe.cmd}" )
	private String probeCmd;

	@Autowired
	private InfoService infoService;
	
	public boolean performProbe(ProbeRequest probe ) {

		logger.info("performing probe: {}",probe);
		boolean success = false;
		String localFilename = null;
		try {
			localFilename = downloadToLocalFile(probe.getUrl());
			VideoInfo videoInfo = probeFile(localFilename);

			// add file name parsed from url
			String baseName = FilenameUtils.getBaseName(probe.getUrl());
			String extension = FilenameUtils.getExtension(probe.getUrl());
			String videoName = baseName + "." + extension;
			videoInfo.setName(videoName);

			logger.info("VideoInfo {}",videoInfo);
			infoService.updateVideoInfoService(videoInfo);
			
			success = true;
			
		} catch (IOException|InvalidExitValueException|TimeoutException|InterruptedException e) {
			logger.error("Error probing {}: {}",probe, e);
		} finally {
			try {
				Files.delete( Paths.get(localFilename) );
			} catch (IOException e) {
				logger.error("Error deleting {}: {}",probe, e);
			}
		}
		return success;
	}


	private String runProbe(String localFilename) throws InvalidExitValueException, IOException, InterruptedException, TimeoutException {

		String cmd = probeCmd + " " + localFilename;
		String output = new ProcessExecutor().commandSplit(cmd)
				.readOutput(true).execute()
				.outputUTF8();    
		logger.debug("probe out {}", output);
		return output;
	}

	private VideoInfo probeFile(String localFilename) throws InvalidExitValueException, IOException, InterruptedException, TimeoutException {

		String output = runProbe(localFilename);

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String,Object> probeOutMap = objectMapper.readValue(output,Map.class);
		Map<String,Object> format = (Map<String, Object>) probeOutMap.get("format");

		VideoInfo vi = new VideoInfo();
		vi.setFormatName( format.get("format_name").toString() );
		vi.setDuration( Double.valueOf( format.get("duration").toString() ) );

		logger.info("probed VideoInfo {}", vi);
		return vi;
	}

	String downloadToLocalFile(String url) throws MalformedURLException, IOException {
		InputStream in = new URL(url).openStream();
		String localFileName = UUID.randomUUID().toString();
		logger.info("downloading from url: {} to local: {}", url, localFileName);
		Files.copy(in, Paths.get(localFileName), StandardCopyOption.REPLACE_EXISTING);
		return localFileName;
	}


}
