package de.juli.jobfxclient.viewmodel;

import de.juli.jobmodel.model.Document;

public class ViewDocument {
	private Document document;

	public ViewDocument(Document document) {
		super();
		this.document = document;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	@Override
	public String toString() {
		return this.document.getName();
	}

}
