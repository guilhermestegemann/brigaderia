package br.com.brigaderia.exception;

public class EstoqueNegativoException extends BrigaderiaException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EstoqueNegativoException(){
		super("Não é permitido estoque negativo.");
	}
	public EstoqueNegativoException(String msg){
		super(msg);
	}
	public EstoqueNegativoException(Throwable t){
		super(t);
	}
	

}
