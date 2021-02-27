package de.juli.jobweb.exeptions;

import com.sun.star.uno.RuntimeException;

public class ShittHappensExeption extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public ShittHappensExeption(){
		super();
	}
	public ShittHappensExeption(String msg){
		super(msg);
	}
}
