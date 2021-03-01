package de.juli.jobapp.jobmodel.exeptions;

import com.sun.star.uno.RuntimeException;

public class DocumentServiceExeption extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public DocumentServiceExeption(){
		super();
	}
	public DocumentServiceExeption(String msg){
		super(msg);
	}
}
