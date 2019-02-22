package com.example.videoprobe.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "probe")
public class VideoProbeController {


    private static final Logger logger = LoggerFactory.getLogger(VideoProbeController.class);

    @Autowired
    JmsTemplate jmsTemplate;
    
    private final String PROBE_QUEUE = "probe";
    
    /**
     * Probe operation.
     * 
     * @param videoUrl
     * @return the created hero
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void createHero(@RequestBody ProbeRequest probe,
            HttpServletRequest request, HttpServletResponse response) {

    	jmsTemplate.convertAndSend(PROBE_QUEUE,probe);
    	logger.info("send probe: {} to MQ {}", probe, PROBE_QUEUE);
        
    }

}
