package br.com.brigaderia.exception;

public class OrdemProduzidaException extends BrigaderiaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public OrdemProduzidaException(){
		super("A Ordem de Produção já está produzida.");
	}
	public OrdemProduzidaException(String msg){
		super(msg);
	}
	public OrdemProduzidaException(Throwable t){
		super(t);
	}
}