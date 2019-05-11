
package com.scheduler.dao.impl;


import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.scheduler.dao.JobDao;
import com.scheduler.domain.JobDetails;
import com.scheduler.model.QuartzModel;
import com.scheduler.utility.GlobalConstants;
import com.scheduler.utility.JobRowMapper;

/**
 * @author kartavya.soni
 *
 */

@Repository
public class JobDaoImpl implements JobDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JobDaoImpl.class);
	private static final String WHERE = " WHERE ";
	private static final String JOB_NAME = " JOB_NAME ";
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	DataSource dataSource;

	@Override
	public JobDetails findJobByJobName(String jobName) {
		JobDetails job  = null;
		try{
			job = jdbcTemplate.queryForObject("SELECT * FROM JOB_DETAILS WHERE JOB_NAME ='"+jobName+"'", new JobRowMapper());
		} catch (DataAccessException ex) {
			LOGGER.error("JobDaoImpl : No data found for jobName", ex);
		}
		return job;
	}

	@Override
	public void updateJobIsActive(String jobName, boolean isActive) {
		StringBuilder query = new StringBuilder("UPDATE JOB_DETAILS SET IS_ACTIVE=? ")
		.append(WHERE)
		.append(JOB_NAME + " = ?");
		jdbcTemplate.update(query.toString(), isActive, jobName);
	}

	@Override
	public void updateJobStatus(String jobName, String jobStatus) {
		StringBuilder query = new StringBuilder("UPDATE JOB_DETAILS SET JOB_STATUS='")
		.append(jobStatus).append("'")
		.append(" "+WHERE)
		.append(" "+JOB_NAME + " = '").append(jobName).append("'");
		jdbcTemplate.update(query.toString());
	}

	@Override
	public List<JobDetails> findAll() {
		return jdbcTemplate.query("SELECT * FROM JOB_DETAILS", new JobRowMapper());
	}

	@Override
	public void save(JobDetails entity, boolean isNew) {
		if (isNew) {
			String insertBolHistorySql ="insert into JOB_DETAILS(JOB_NAME , JOB_CLASS , CRON_EXPSN ,IS_ACTIVE  ,JOB_STATUS , START_TS,END_TS,UPDT_BY,UPDT_TS,LAST_RUN_TS,SCHD_TYPE,MINUTE_INTRVL,HOUR_INTRVL,DAY_INTRVL,MONTH_DATE,MONTH,DAY_OF_WEEK,TIME) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
			jdbcTemplate.update(insertBolHistorySql,entity.getJobName(),entity.getJobClass(),entity.getCronExpression(),true,entity.getJobStatus(),entity.getStartTs(),entity.getEndTs(),entity.getUpdatedBy(),entity.getUpdatedTs(),entity.getLastRunTime(),entity.getScheduleType(),entity.getMinuteInterval(),entity.getHourInterval(),entity.getDayInterval(),entity.getMonthDate(),entity.getMonth(),entity.getDayOfWeek(),entity.getTime());
		} else {
			String updateQuery = "update JOB_DETAILS set JOB_NAME=?, JOB_CLASS=?, CRON_EXPSN=?, IS_ACTIVE=?, JOB_STATUS=?, START_TS=?, END_TS=?,UPDT_BY=?,UPDT_TS=?,LAST_RUN_TS=?,SCHD_TYPE=?, MINUTE_INTRVL=?, HOUR_INTRVL=?, DAY_INTRVL=?, MONTH_DATE=?, MONTH=?, DAY_OF_WEEK=?, TIME=? WHERE ID="+entity.getId();
			jdbcTemplate.update(updateQuery,entity.getJobName(),entity.getJobClass(),entity.getCronExpression(),true,entity.getJobStatus(),entity.getStartTs(),entity.getEndTs(),entity.getUpdatedBy(),entity.getUpdatedTs(),entity.getLastRunTime(),entity.getScheduleType(),entity.getMinuteInterval(),entity.getHourInterval(),entity.getDayInterval(),entity.getMonthDate(),entity.getMonth(),entity.getDayOfWeek(),entity.getTime());
		}
	}
	@Override
	public void persistJobDetail(QuartzModel model, String cronExpression, Timestamp startTs, @SuppressWarnings("rawtypes") Class className) throws SchedulerException {
		LOGGER.debug("Scheduler Service: Updating the Job :" +model.getJobName()+" with CronExpression : "+cronExpression);
		JobDetails job = null;
			job = findJobByJobName(model.getJobName());
		// Create New Job if not exist
		boolean isNew = true;
		if (null != job){
			isNew = false;
		} else {
			job = new JobDetails();
		}
		job.setJobName(model.getJobName());
		job.setCronExpression(cronExpression);
		job.setUpdatedTs(new Timestamp(System.currentTimeMillis()));
		job.setScheduleType(model.getScheduleRadio());
		job.setMinuteInterval(model.getMinuteInterval());
		job.setHourInterval(model.getHourInterval());
		job.setDayInterval(model.getDayInterval());
		job.setMonthDate(model.getMonthDate());
		job.setMonth(model.getMonth());
		job.setDayOfWeek(model.getDayOfWeek());
		job.setTime(model.getTime());
		job.setStartTs(startTs);
		job.setActive(true);
		job.setJobStatus(GlobalConstants.JobStatus.SCHEDULED.toString());
		job.setJobClass(className.getName());
		save(job, isNew);
	}
	@Override
	public List<Map<String, Object>> findTrigger() {
		List<Map<String, Object>> obj  = null;
		try{
			obj = jdbcTemplate.queryForList("SELECT JOB_NAME,START_TIME,END_TIME,NEXT_FIRE_TIME,PREV_FIRE_TIME FROM qrtz_triggers");
		} catch (DataAccessException ex) {
			LOGGER.error("JobDaoImpl : No data found for jobName", ex);
		}
		return obj;
	}
}
