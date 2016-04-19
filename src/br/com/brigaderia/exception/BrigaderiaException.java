package br.com.brigaderia.exception;

public class BrigaderiaException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -40726636170956732L;
	
	public BrigaderiaException(){
		super("Ocorreu um erro inesperado. Entre em contato com o administrador do sistema.");
	}
	public BrigaderiaException(String msg){
		super(msg);
	}
	public BrigaderiaException(Throwable t){
		super(t);
	}
}