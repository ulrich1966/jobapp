package de.juli.jobfxclient.util;

public enum CurrentAppState {
	SUCCESS("success"), FAIL("fail"), ABORT("abort");

	private String msg;
	private String state;
	
	CurrentAppState(String state){
		this.state = state;
	}

	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		if(msg == null || msg.isEmpty()) {
			msg = String.format("State: %s", state);			
		} else {
			msg = String.format("%s\nState: %s", msg, state);			
		}
		this.msg = msg;
	}		
}
