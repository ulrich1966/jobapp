package de.juli.jobmodel.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import de.juli.jobmodel.enums.JobState;

@Entity
public class State extends Model{
	private Long timeStamp;
	private String note;
	@Enumerated(EnumType.STRING)
	private JobState jobState;

	public State() {
		super();
	}

	public State(JobState state) {
		super();
		this.setJobState(state);
	}
	
	public State(JobState state, String note) {
		this(state);
		this.note = note;
	}
	
	public JobState getJobState() {
		return jobState;
	}

	public void setJobState(JobState jobState) {
		this.jobState = jobState;
		this.timeStamp = new Date().getTime();
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("State [timeStamp=");
		builder.append(timeStamp);
		builder.append(", note=");
		builder.append(note);
		builder.append(", jobState=");
		builder.append(jobState);
		builder.append("]");
		return builder.toString();
	}
}
