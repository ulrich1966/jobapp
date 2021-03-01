package de.juli.jobapp.jobweb.viewmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.juli.jobmodel.model.Email;
import de.juli.jobmodel.model.Letter;
import de.juli.jobmodel.model.Source;
import de.juli.jobmodel.model.Vita;

public class DocumentSelections implements Serializable {
	private static final long serialVersionUID = 1L;
	private Vita[] vitas; 
	private Letter[] letters; 
	private Email[] emails; 
	private Source[] sources;
	private Vita selectedVita; 
	private Letter selectedLetter; 
	private Email selectedEmail; 
	private Source selectedSource;

	public Vita[] getVitas() {
		return vitas;
	}
	public List<Vita> getVitasAsList() {
		return new ArrayList<Vita>(Arrays.asList(vitas));
	}
	public void setVitas(Vita[] vitas) {
		this.vitas = vitas;
	}
	public void setVitas(List<Vita> list) {
		this.vitas = list.toArray(new Vita[list.size()]);
	}
	
	public Letter[] getLetters() {
		return letters;
	}
	public List<Letter> getLettersAsList() {
		return new ArrayList<Letter>(Arrays.asList(letters));
	}	
	public void setLetters(Letter[] letters) {
		this.letters = letters;
	}
	public void setLetters(List<Letter> list) {
		this.letters = list.toArray(new Letter[list.size()]);
	}
	
	public Email[] getEmails() {
		return emails;
	}
	public List<Email> getEmailsAsList() {
		return new ArrayList<Email>(Arrays.asList(emails));
	}	
	public void setEmails(Email[] emails) {
		this.emails = emails;
	} 
	public void setEmails(List<Email> list) {
		this.emails = list.toArray(new Email[list.size()]);
	}
	public Vita getSelectedVita() {
		return selectedVita;
	}
	public void setSelectedVita(Vita selectedVita) {
		this.selectedVita = selectedVita;
	}
	public Letter getSelectedLetter() {
		return selectedLetter;
	}
	public void setSelectedLetter(Letter selectedLetter) {
		this.selectedLetter = selectedLetter;
	}
	public Email getSelectedEmail() {
		return selectedEmail;
	}
	public void setSelectedEmail(Email selectedEmail) {
		this.selectedEmail = selectedEmail;
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
	public Source getSelectedSource() {
		return selectedSource;
	}
	public void setSelectedSource(Source selectedSource) {
		this.selectedSource = selectedSource;
	}
}
