package com.scheduler.utility;

public interface GlobalConstants {
	
	// Job Scheduling
	public String JOB_GROUP ="accelerator-group";
	public Integer SMS = 0;
	public Integer HSM = 1;
	
	Integer OBJECT_FOUND = 200;
	Integer OBJECT_NOT_FOUND = 0;
	
	public String YEARLY ="yearly";
	public String MONTHLY ="monthly";
	public String WEEKLY ="weekly";
	public String DAILY ="daily";
	public String HOURLY ="hourly";
	public String MINUTELY ="minutes";
	String success  = "success";
	

	public enum TriggerType {
		 SMS_TRIGGER,HMS_TRIGGER
	}
	
	public enum JobType {
		 SMS_JOB,HMS_JOB
	}
	
	public enum JobStatus {
		STARTED, SCHEDULED, STOPPED,PAUSED
	}
	public static final String JOB_PARAMETERS_REGEX = "([\\w\\.-_\\)\\(]+=[^,\\n]*[,\\n])*([\\w\\.-_\\)\\(]+=[^,]*$)";

    /**
     * job name
     */
    public static final String JOB_NAME = "jobName";

    /**
     * job run date
     */
    public static final String JOB_RUN_DATE = "jobRunDate";

    /**
     * Quartz group
     */
    public static final String QUARTZ_GROUP = "quartzGroup";

    /**
     * Quartz trigger name suffix
     */
    public static final String TRIGGER_SUFFIX = "QuartzTrigger";

    /**
     * Bean names
     */
    public static final String JOB_SERVICE_BEAN = "jobService";
    public static final String JOB_DATASTORE_BEAN = "batchDataStore";
    
     /**
     * Messages
     */
    public static final String JOB_IS_NOT_SCHEDULED = "Not Scheduled";
    public static final String JOB_IS_SCHEDULED = "Scheduled";
    
    /**
     * Action
     */
    public static final String ACTION_SCHEDULE = "Schedule";
    public static final String ACTION_UNSCHEDULE = "Un-Schedule";
}
