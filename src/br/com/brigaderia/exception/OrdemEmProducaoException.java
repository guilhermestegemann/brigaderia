package br.com.brigaderia.exception;

public class OrdemEmProducaoException extends BrigaderiaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public OrdemEmProducaoException(){
		super("A Ordem de Produção já está sendo produzida.");
	}
	public OrdemEmProducaoException(String msg){
		super(msg);
	}
	public OrdemEmProducaoException(Throwable t){
		super(t);
	}
}