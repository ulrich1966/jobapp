package de.juli.jobweb.viewmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.juli.jobmodel.enums.Title;
import de.juli.jobmodel.model.Document;
import de.juli.jobmodel.model.Source;

@Deprecated
public class JobSelections {
	private Title[] titles; 
	private Document[] vitas; 
	private Document[] letters; 
	private Document[] emails; 
	private Source[] sources;
	
	public Title[] getTitles() {
		return titles;
	}
	public List<Title> getTitlesAsList() {
		return new ArrayList<Title>(Arrays.asList(titles));
	}
	public void setTitles(Title[] titles) {
		this.titles = titles;
	}
	public void setTitles(List<Title> list) {
		this.titles = list.toArray(new Title[list.size()]);
	}
	
	public Source[] getSources() {
		return sources;
	}
	public List<Source> getSourcesAsList() {
		return new ArrayList<Source>(Arrays.asList(sources));
	}
	public void setSources(Source[] sources) {
		this.sources = sources;
	}
	public void setSources(List<Source> list) {
		this.sources = list.toArray(new Source[list.size()]);
	}
	
	public Document[] getVitas() {
		return vitas;
	}
	public List<Document> getVitasAsList() {
		return new ArrayList<Document>(Arrays.asList(vitas));
	}
	public void setVitas(Document[] vitas) {
		this.vitas = vitas;
	}
	public void setVitas(List<Document> list) {
		this.vitas = list.toArray(new Document[list.size()]);
	}
	
	public Document[] getLetters() {
		return letters;
	}
	public List<Document> getLettersAsList() {
		return new ArrayList<Document>(Arrays.asList(letters));
	}	
	public void setLetters(Document[] letters) {
		this.letters = letters;
	}
	public void setLetters(List<Document> list) {
		this.letters = list.toArray(new Document[list.size()]);
	}
	
	public Document[] getEmails() {
		return emails;
	}
	public List<Document> getEmailsAsList() {
		return new ArrayList<Document>(Arrays.asList(emails));
	}	
	public void setEmails(Document[] emails) {
		this.emails = emails;
	} 
	public void setEmails(List<Document> list) {
		this.emails = list.toArray(new Document[list.size()]);
	}
}
