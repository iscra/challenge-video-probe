package com.example.videoprobe.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.zeroturnaround.exec.InvalidExitValueException;

@Component
public class ProbeReceiver {

	private static final Logger logger = LoggerFactory.getLogger(ProbeReceiver.class);

	@Autowired
	ProbeWorker probeWorker;

	@JmsListener(destination = "probe", containerFactory = "workerJMSFactory")
	public void receiveMessage(ProbeRequest probe ) {
		try {
			logger.info("worker received probe: {}",probe);
			if ( probeWorker.performProbe(probe) ) {
				logger.info("probe success: {}",probe);
			} else {
				logger.info("probe failed: {}",probe);
				// TODO potential repeat later of this job
			}
		} catch(RuntimeException e) {
			logger.info("RuntimeException {}",e);
			// TODO potential repeat later of this job
		}

	}




}
