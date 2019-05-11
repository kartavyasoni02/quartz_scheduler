package com.scheduler.job;
/**
 * @author kartavya.soni
 */

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SMSJobLauncher implements QuartzJob {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SMSJobLauncher.class);
	
	private static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("HH:mm:ss");
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		LOGGER.info(SMSJobLauncher.class.getName()+" started at - " + DATEFORMAT.format(new Date()));
		
		LOGGER.info(SMSJobLauncher.class.getName()+" ended at - " + DATEFORMAT.format(new Date()));
	}

}
