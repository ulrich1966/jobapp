package de.juli.jobfxclient.viewmodel;

import de.juli.jobmodel.model.Source;

public class ViewJobAdSource {
	private Source source;

	public ViewJobAdSource(Source source) {
		super();
		this.source = source;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return source.getName();
	}
}
