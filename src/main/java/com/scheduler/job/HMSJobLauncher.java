package com.scheduler.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HMSJobLauncher implements QuartzJob {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HMSJobLauncher.class);
	
	private static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("HH:mm:ss");
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		LOGGER.info(HMSJobLauncher.class.getName()+" started at - " + DATEFORMAT.format(new Date()));
		
		LOGGER.info(HMSJobLauncher.class.getName()+" ended at - " + DATEFORMAT.format(new Date()));
	}
}
