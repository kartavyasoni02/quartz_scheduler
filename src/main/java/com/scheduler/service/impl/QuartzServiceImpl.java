package com.scheduler.service.impl;

/**
 * @author kartavya.soni
 */

import static com.cronutils.model.CronType.QUARTZ;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import com.scheduler.dao.JobDao;
import com.scheduler.domain.JobDetails;
import com.scheduler.job.HMSJobLauncher;
import com.scheduler.job.SMSJobLauncher;
import com.scheduler.model.QuartzModel;
import com.scheduler.service.QuartzService;
import com.scheduler.utility.CronGenetorUtility;
import com.scheduler.utility.DateUtility;
import com.scheduler.utility.GlobalConstants;
import com.scheduler.utility.QuartzScheduler;

@Service
@Transactional
public class QuartzServiceImpl implements QuartzService {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuartzServiceImpl.class);
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	private JobDao jobDao;

	@Override

	public QuartzModel scheduleJobs(QuartzModel model) {
		LOGGER.debug("Scheduler Service: SchedulerModel is: " + model.toString());
		Scheduler sched = schedulerFactoryBean.getScheduler();
		try{
			String cronExpression = CronGenetorUtility.generateCronExpression(model);
			Timestamp startTs = new Timestamp(System.currentTimeMillis());
			if(model.getJobName().equals(GlobalConstants.JobType.SMS_JOB.toString())){
				jobDao.persistJobDetail(model, cronExpression, startTs, SMSJobLauncher.class);
				QuartzScheduler.getInstance().scheduleJob(SMSJobLauncher.class, model, cronExpression, GlobalConstants.TriggerType.SMS_TRIGGER, startTs, sched);
			}
			if(model.getJobName().equals(GlobalConstants.JobType.HMS_JOB.toString())){
				jobDao.persistJobDetail(model, cronExpression, startTs, HMSJobLauncher.class);
				QuartzScheduler.getInstance().scheduleJob(HMSJobLauncher.class, model, cronExpression, GlobalConstants.TriggerType.HMS_TRIGGER, startTs,sched);
			}
			model.setResponseCode(GlobalConstants.OBJECT_FOUND);

		} catch (Exception ex) {
			LOGGER.error("Error in saveAndScheduleAllJobs() : ",ex);
		}
		return model;
	}


	@Override
	public String stopJob(String jobName) {
		LOGGER.debug("Scheduler Service: Stopping the Job {}", jobName);
		Scheduler sched = schedulerFactoryBean.getScheduler();
		try {

			if(jobName.equals(GlobalConstants.JobType.SMS_JOB.toString())){
				QuartzScheduler.getInstance().deleteScheduleIfAlreadyScheduled(jobName, GlobalConstants.TriggerType.SMS_TRIGGER.toString(),sched);
				jobDao.updateJobIsActive(jobName, false);
				jobDao.updateJobStatus(jobName, GlobalConstants.JobStatus.PAUSED.toString());
			}
			else if(jobName.equals(GlobalConstants.JobType.HMS_JOB.toString())){
				QuartzScheduler.getInstance().deleteScheduleIfAlreadyScheduled(jobName, GlobalConstants.TriggerType.HMS_TRIGGER.toString(),sched);
				jobDao.updateJobIsActive(jobName, false);
				jobDao.updateJobStatus(jobName, GlobalConstants.JobStatus.PAUSED.toString());
			}else{
				return "job not found";
			}
		} catch (Exception e) {
			LOGGER.error("Error in stopSingleJobs() : ", e);
			LOGGER.debug("Scheduler Service: Exception occurred while Stopping the Job {}", jobName);
		}
		return "Success";
	}


	@Override
	public String startJob(String jobName) {
		LOGGER.debug("Scheduler Service: Start the Job {}", jobName);
		Scheduler sched = schedulerFactoryBean.getScheduler();
		try {

			if(jobName.equals(GlobalConstants.JobType.SMS_JOB.toString())){
				QuartzScheduler.getInstance().resumeScheduleIfAlreadyScheduled(jobName, GlobalConstants.TriggerType.SMS_TRIGGER.toString(),sched);
				jobDao.updateJobIsActive(jobName, true);
				jobDao.updateJobStatus(jobName, GlobalConstants.JobStatus.SCHEDULED.toString());
			}
			else if(jobName.equals(GlobalConstants.JobType.HMS_JOB.toString())){
				QuartzScheduler.getInstance().resumeScheduleIfAlreadyScheduled(jobName, GlobalConstants.TriggerType.HMS_TRIGGER.toString(),sched);
				jobDao.updateJobIsActive(jobName, true);
				jobDao.updateJobStatus(jobName, GlobalConstants.JobStatus.SCHEDULED.toString());
			}else{
				return "job not found";
			}
		} catch (Exception e) {
			LOGGER.error("Error in stopSingleJobs() : ", e);
			LOGGER.debug("Scheduler Service: Exception occurred while Stopping the Job {}", jobName);
		}
		return "Success";
	}




	/**
	 *
	 * @param jobName
	 * @return String
	 */
	public String getScheduledJobDescription(String jobName) {
		String message = GlobalConstants.JOB_IS_NOT_SCHEDULED;
		JobKey jobKey = new JobKey(jobName, GlobalConstants.QUARTZ_GROUP);
		try {
			JobDetail jobDetail = schedulerFactoryBean.getScheduler().getJobDetail(jobKey);
			if (null != jobDetail) {
				List<? extends Trigger> triggersOfJob = schedulerFactoryBean.getScheduler().getTriggersOfJob(jobKey);
				if (null != triggersOfJob && !triggersOfJob.isEmpty()) {
					CronTrigger trigger = (CronTrigger) triggersOfJob.get(0);
					String cronExpression = trigger.getCronExpression();
					CronDescriptor descriptor = CronDescriptor.instance(Locale.US);
					CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(QUARTZ));
					message = descriptor.describe(parser.parse(cronExpression));
				}

			}
		} catch (SchedulerException e) {
			//BatchAdminLogger.getLogger().error(e.getMessage(), e);
		}
		return message;
	}
	@Override
	public List<JobDetails> getAllJobs() {
		LOGGER.debug("Scheduler Service: Getting All the Job From DB.");
		return jobDao.findAll();
	}

	@Override
	public List<QuartzModel> populateAllScheduledJobs() {
		LOGGER.debug("Scheduler Service: populateAllScheduledJobs");
		List<QuartzModel> modelList = null;
		List<JobDetails> jobDetails = jobDao.findAll();
		if (null != jobDetails && !jobDetails.isEmpty()) {
			List<Map<String, Object>> listOfJob = jobDao.findTrigger();
			modelList = new ArrayList<QuartzModel>();
			Map<String, Map<String, Object>> finalJobMap =  this.getMapOfJob(listOfJob);
			for (JobDetails job : jobDetails) {
				QuartzModel model = new QuartzModel();
				model.setJobName(job.getJobName());
				if(finalJobMap!=null){
					Map<String, Object> jobObject = finalJobMap.get(job.getJobName());
					model.setNextFireTime(DateUtility.convertFromTimestampLongToString((long)jobObject.get("NEXT_FIRE_TIME")));	
					if((long)jobObject.get("PREV_FIRE_TIME")>0){
						model.setPreviousFireTime(DateUtility.convertFromTimestampLongToString((long)jobObject.get("PREV_FIRE_TIME")));
					}else{
						model.setPreviousFireTime("N/A");
					}
				}
				model.setStatus(job.getJobStatus());
				model.setScheduleRadio(job.getScheduleType());
				model.setHourInterval(job.getHourInterval());
				model.setDayInterval(job.getDayInterval());
				model.setMinuteInterval(job.getMinuteInterval());
				model.setDayOfWeek(job.getDayOfWeek());
				model.setMonth(job.getMonth());
				model.setMonthDate(job.getMonthDate());
				model.setTime(job.getTime());
				model.setActive(job.isActive());
				modelList.add(model);
			}
		}
		return modelList;
	}

	private Map<String, Map<String, Object>> getMapOfJob(List<Map<String, Object>> listOfJob){
		Map<String, Map<String, Object>> finalMap = null;
		if(listOfJob!=null && !listOfJob.isEmpty()){
			finalMap = new HashMap<String, Map<String,Object>>();
			for (Map<String, Object> map : listOfJob) {
				finalMap.put((String) map.get("JOB_NAME"), map);
			}
		}
		return finalMap;
	}


	@Override
	public QuartzModel getScheduledJob(String jobName) {
		JobDetails job = jobDao.findJobByJobName(jobName);
		QuartzModel model =null;
		if(null!=job){
			model = new QuartzModel();
			model.setJobName(job.getJobName());
			model.setStatus(job.getJobStatus());
			model.setScheduleRadio(job.getScheduleType());
			model.setHourInterval(job.getHourInterval());
			model.setDayInterval(job.getDayInterval());
			model.setMinuteInterval(job.getMinuteInterval());
			model.setDayOfWeek(job.getDayOfWeek());
			model.setMonth(job.getMonth());
			model.setMonthDate(job.getMonthDate());
			model.setTime(job.getTime());
			model.setActive(job.isActive());
		}
		return model;
	}

/*	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.getTimeInMillis());
	}*/
}