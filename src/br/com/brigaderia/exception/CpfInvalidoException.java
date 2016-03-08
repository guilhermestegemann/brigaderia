package br.com.brigaderia.exception;

public class CpfInvalidoException extends BrigaderiaException{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CpfInvalidoException(){
		super("O Cpf informado é inválido! Por favor, verifique!");
	}
	public CpfInvalidoException(String msg){
		super(msg);
	}
	public CpfInvalidoException(Throwable t){
		super(t);
	}
	

}
