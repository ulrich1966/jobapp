package de.juli.jobweb.viewmodel;

import java.util.List;

import de.juli.jobmodel.model.Company;
import de.juli.jobmodel.model.Document;
import de.juli.jobmodel.model.History;
import de.juli.jobmodel.model.Source;

public class JobDataList {
	private Company company;
	private History historyLastEntry;
	private List<History> historys;
	private Document document;
	private Source jobAdSource;

	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public History getHistoryLastEntry() {
		return historyLastEntry;
	}
	public void setHistoryLastEntry(History historyLastEntry) {
		this.historyLastEntry = historyLastEntry;
	}
	public List<History> getHistorys() {
		return historys;
	}
	public void setHistorys(List<History> historys) {
		this.historys = historys;
	}
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public Source getJobAdSource() {
		return jobAdSource;
	}
	public void setJobAdSource(Source jobAdSource) {
		this.jobAdSource = jobAdSource;
	}
}
