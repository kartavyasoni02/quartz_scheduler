/**
 * 
 */
package com.scheduler.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.quartz.SchedulerException;

import com.scheduler.domain.JobDetails;
import com.scheduler.model.QuartzModel;

/**
 * @author kartavya.soni
 *
 */
public interface JobDao {
	public JobDetails  findJobByJobName(String jobName);
	public void updateJobIsActive(String jobName, boolean isActive);
	public void updateJobStatus(String jobName, String jobStatus);
	public List<JobDetails> findAll();
	public void save(JobDetails entity, boolean isNew);
	@SuppressWarnings("rawtypes")
	public void persistJobDetail(QuartzModel model, String cronExpression,
			Timestamp startTs, Class className) throws SchedulerException;
	public List<Map<String, Object>> findTrigger();
}
