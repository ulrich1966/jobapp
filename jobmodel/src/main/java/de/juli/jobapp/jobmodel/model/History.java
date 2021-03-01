package de.juli.jobapp.jobmodel.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import de.juli.jobapp.jobmodel.enums.AppHistory;

@Entity
public class History extends Model{
	private Long timeStamp;
	private String note;
	@Enumerated(EnumType.STRING)
	private AppHistory appHistory;

	public History() {
		super();
	}

	public History(AppHistory appHistory) {
		super();
		this.setAppHistory(appHistory);
	}
	
	public History(AppHistory appHistory, String note) {
		this(appHistory);
		this.note = note;
	}
	
	public AppHistory getAppHistory() {
		return appHistory;
	}
	
	public void setAppHistory(AppHistory appHistory) {
		this.appHistory = appHistory;
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
		builder.append("History [timeStamp=");
		builder.append(timeStamp);
		builder.append(", note=");
		builder.append(note);
		builder.append(", applicationState=");
		builder.append(appHistory.name());
		builder.append("]");
		return builder.toString();
	}

	
}
