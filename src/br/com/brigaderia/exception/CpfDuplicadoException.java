package br.com.brigaderia.exception;

public class CpfDuplicadoException extends BrigaderiaException{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CpfDuplicadoException(){
		super("Já existe um cliente com o mesmo CPF! Favor verificar!");
	}
	public CpfDuplicadoException(String msg){
		super(msg);
	}
	public CpfDuplicadoException(Throwable t){
		super(t);
	}
	

}
