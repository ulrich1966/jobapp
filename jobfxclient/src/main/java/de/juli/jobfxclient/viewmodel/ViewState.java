package de.juli.jobfxclient.viewmodel;

import de.juli.jobmodel.model.ApplicationState;

public class ViewState {
	private ApplicationState state;

	public ViewState(ApplicationState state) {
		super();
		this.state = state;
	}

	public ApplicationState getState() {
		return state;
	}

	public void setState(ApplicationState state) {
		this.state = state;
	}

	
}
