package br.com.brigaderia.exception;

public class ProdutosNaoInformadosException extends BrigaderiaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProdutosNaoInformadosException(){
		super("Os produtos não foram informados.");
	}
	public ProdutosNaoInformadosException(String msg){
		super(msg);
	}
	public ProdutosNaoInformadosException(Throwable t){
		super(t);
	}
	

}