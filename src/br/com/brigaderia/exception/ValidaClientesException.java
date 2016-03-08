package br.com.brigaderia.exception;

public class ValidaClientesException extends BrigaderiaException{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ValidaClientesException(){
		super("Favor preencher os campos obrigatórios!");
	}
	public ValidaClientesException(String msg){
		super(msg);
	}
	public ValidaClientesException(Throwable t){
		super(t);
	}
	

}
