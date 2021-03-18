package de.juli.jobapp.jobmodel.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
	  @NamedQuery(name="Account.findAll", query="SELECT model FROM Account model"),
	  @NamedQuery(name="Account.findByName", query="SELECT model FROM Account model WHERE model.name = :name")
	})
public class Account extends Model{
	@Column(unique = true)
	private String name;
	private String pass;
	private Integer port;
	private String user;
	@Column(unique = true)
	private String sender;
	private String smtpPass;
	private String profillink;
	private String smtp;
	private String styleTheme;
	@OneToMany(mappedBy="account", cascade=CascadeType.ALL)
	private List<Job> jobs;
	@OneToOne(cascade=CascadeType.ALL)
	private SenderAddress address;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSmtpPass() {
		return smtpPass;
	}
	public void setSmtpPass(String smtpPass) {
		this.smtpPass = smtpPass;
	}
	public String getProfillink() {
		return profillink;
	}
	public void setProfillink(String profillink) {
		this.profillink = profillink;
	}
	public String getSmtp() {
		return smtp;
	}
	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}
	public String getStyleTheme() {
		return styleTheme;
	}
	public void setStyleTheme(String styleTheme) {
		this.styleTheme = styleTheme;
	}
	public List<Job> getJobs() {
		return jobs;
	}
	public SenderAddress getAddress() {
		return address;
	}
	public void setAddress(SenderAddress address) {
		this.address = address;
	}
	public void addJob(Job job) {
		if(this.jobs == null) {
			this.jobs = new ArrayList<>();
		}
		this.jobs.add(job);
		job.setAccount(this);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Account [name=");
		builder.append(name);
		builder.append(", pass=");
		builder.append(pass);
		builder.append(", port=");
		builder.append(port);
		builder.append(", user=");
		builder.append(user);
		builder.append(", sender=");
		builder.append(sender);
		builder.append(", smtpPass=");
		builder.append(smtpPass);
		builder.append(", profillink=");
		builder.append(profillink);
		builder.append(", smtp=");
		builder.append(smtp);
		builder.append(", styleTheme=");
		builder.append(styleTheme);
		builder.append("]");
		return builder.toString();
	}
}
