package de.juli.jobapp.jobmodel.exeptions;

import com.sun.star.uno.RuntimeException;

public class ShitHappendsExeption extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public ShitHappendsExeption(){
		super();
	}
	public ShitHappendsExeption(String msg){
		super(msg);
	}
}
