package de.juli.jobapp.jobmodel.exeptions;

import com.sun.star.uno.RuntimeException;

public class ModelNotFoundExeption extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public ModelNotFoundExeption(){
		super();
	}
	public ModelNotFoundExeption(String msg){
		super(msg);
	}
}
