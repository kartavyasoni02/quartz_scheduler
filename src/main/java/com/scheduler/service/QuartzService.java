package com.scheduler.service;

/**
 * @author kartavya.soni
 */

import java.util.List;

import com.scheduler.domain.JobDetails;
import com.scheduler.model.QuartzModel;

public interface QuartzService {
	 public QuartzModel scheduleJobs(QuartzModel model);
	
	 public String stopJob(String job);

	List<JobDetails> getAllJobs();

	List<QuartzModel> populateAllScheduledJobs();

	public QuartzModel getScheduledJob(String jobName);

	public String startJob(String job);
	
}