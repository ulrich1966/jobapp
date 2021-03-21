package de.juli.jobapp.jobweb.exeptions;

import com.sun.star.uno.RuntimeException;

public class NoCredtialsExeption extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public NoCredtialsExeption(){
		super();
	}
	public NoCredtialsExeption(String msg){
		super(msg);
	}
}
