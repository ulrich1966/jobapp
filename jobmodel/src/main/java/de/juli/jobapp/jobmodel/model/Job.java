package de.juli.jobapp.jobmodel.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({ @NamedQuery(name = "Job.findAll", query = "SELECT model FROM Job model"), @NamedQuery(name = "Job.findByTitle", query = "SELECT model FROM Job model WHERE model.title = :title"), })
public class Job extends Model {
	private String title;
	private String jobfunction;
	private String note;
	private String webLink;
	private String localDocDir;
	private Date jobAdDate;
	private Integer salary;
	private Boolean isListed;
	@OneToOne(cascade = CascadeType.ALL)
	private Source source;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "company", nullable = false)
	private Company company;
	@OneToOne(cascade = CascadeType.ALL)
	private Vita vita;
	@OneToOne(cascade = CascadeType.ALL)
	private Letter letter;
	@OneToOne(cascade = CascadeType.ALL)
	private Email email;
	@OneToOne(cascade = CascadeType.ALL)
	private Pdf pdf;
	@OneToMany(cascade = CascadeType.ALL)
	private List<State> states;
	@OneToMany(cascade = CascadeType.ALL)
	private List<History> historys;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "account", nullable = false)
	private Account account;

	public Job() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getJobfunction() {
		return jobfunction;
	}

	public void setJobfunction(String jobfunction) {
		this.jobfunction = jobfunction;
	}

	public Date getJobAdDate() {
		return jobAdDate;
	}

	public void setJobAdDate(Date jobAdDate) {
		this.jobAdDate = jobAdDate;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
		company.addJob(this);
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public List<State> getStates() {
		return states;
	}

	public List<History> getHistorys() {
		return historys;
	}

	public void addState(State state) {
		if (this.states == null) {
			this.states = new ArrayList<>();
		}
		if (!states.contains(state)) {
			this.states.add(state);
		}
	}

	public void addHistory(History history) {
		if (this.historys == null) {
			this.historys = new ArrayList<>();
		}
		if (!historys.contains(history)) {
			this.historys.add(history);
		}
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public State getStateLastEntry() {
		State state = null;
		if (states != null && !states.isEmpty()) {
			state = states.get(states.size() - 1);
		}
		return state;
	}

	public History getHistoryLastEntry() {
		History history = null;
		if (historys != null && !historys.isEmpty()) {
			history = historys.get(historys.size() - 1);
		}
		return history;
	}

	public String getWebLink() {
		return webLink;
	}

	public void setWebLink(String webLink) {
		this.webLink = webLink;
	}

	public String getLocalDocDir() {
		return localDocDir;
	}

	public void setLocalDocDir(String localDocDir) {
		this.localDocDir = localDocDir;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Vita getVita() {
		return vita;
	}

	public void setVita(Vita vita) {
		this.vita = vita;
	}

	public Letter getLetter() {
		return letter;
	}

	public void setLetter(Letter letter) {
		this.letter = letter;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public Pdf getPdf() {
		return pdf;
	}

	public void setPdf(Pdf pdf) {
		this.pdf = pdf;
	}

	public Boolean getIsListed() {
		return isListed;
	}

	public void setIsListed(Boolean isListed) {
		this.isListed = isListed;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Job [title=");
		builder.append(title);
		builder.append(", jobfunction=");
		builder.append(jobfunction);
		builder.append(", note=");
		builder.append(note);
		builder.append(", webLink=");
		builder.append(webLink);
		builder.append(", localDocDir=");
		builder.append(localDocDir);
		builder.append(", jobAdDate=");
		builder.append(jobAdDate);
		builder.append(", salary=");
		builder.append(salary);
		builder.append(", isListed=");
		builder.append(isListed);
		builder.append(", vita=");
		builder.append(vita);
		builder.append(", letter=");
		builder.append(letter);
		builder.append(", email=");
		builder.append(email);
		builder.append(", pdf=");
		builder.append(pdf);
		builder.append(", account=");
		builder.append(account.getName());
		builder.append("]");
		return builder.toString();
	}
}
