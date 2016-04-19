package br.com.brigaderia.exception;

public class DataNascimentoInvalidaException extends BrigaderiaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DataNascimentoInvalidaException(){
		super("A data de nascimento informada é maior que a data atual!");
	}
	public DataNascimentoInvalidaException(String msg){
		super(msg);
	}
	public DataNascimentoInvalidaException(Throwable t){
		super(t);
	}
}