package br.com.brigaderia.exception;

public class OrdemNaoEmProducaoException extends BrigaderiaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public OrdemNaoEmProducaoException(){
		super("A Ordem de Produção não está em produção.");
	}
	public OrdemNaoEmProducaoException(String msg){
		super(msg);
	}
	public OrdemNaoEmProducaoException(Throwable t){
		super(t);
	}
}