package br.com.brigaderia.exception;

public class OrdemNaoProduzidaException extends BrigaderiaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public OrdemNaoProduzidaException(){
		super("A Ordem de Produção não está produzida.");
	}
	public OrdemNaoProduzidaException(String msg){
		super(msg);
	}
	public OrdemNaoProduzidaException(Throwable t){
		super(t);
	}
}