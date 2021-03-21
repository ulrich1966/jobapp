package de.juli.jobapp.jobweb.exeptions;

import com.sun.star.uno.RuntimeException;

public class AttachmentNotAvailableExeption extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public AttachmentNotAvailableExeption(){
		super();
	}
	public AttachmentNotAvailableExeption(String msg){
		super(msg);
	}
}
