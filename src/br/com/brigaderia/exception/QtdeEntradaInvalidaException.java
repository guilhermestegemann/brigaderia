package br.com.brigaderia.exception;

public class QtdeEntradaInvalidaException extends BrigaderiaException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public QtdeEntradaInvalidaException(){
		super("Qtde Entrada deve ser maior que zero! Verifique!");
	}
	public QtdeEntradaInvalidaException(String msg){
		super(msg);
	}
	public QtdeEntradaInvalidaException(Throwable t){
		super(t);
	}
	

}
