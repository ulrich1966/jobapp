package de.juli.jobfxclient.viewmodel;

import de.juli.jobmodel.model.Title;

public class ViewTitle {
	private Title title;

	public ViewTitle(Title title) {
		super();
		this.title = title;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return title.getName();
	}
}
