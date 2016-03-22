package br.com.brigaderia.exception;

public class UnMedidaInvalidaException extends BrigaderiaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnMedidaInvalidaException(){
		super("Unidade de medida inválida! Utilize apenas letras!");
	}
	public UnMedidaInvalidaException(String msg){
		super(msg);
	}
	public UnMedidaInvalidaException(Throwable t){
		super(t);
	}
	

}