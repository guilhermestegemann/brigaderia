package br.com.brigaderia.exception;

public class ProdutoVinculadoEmPerdaException extends BrigaderiaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProdutoVinculadoEmPerdaException(){
		super("Esse produto possui vínculo com lançamentos de Perda! Por isso não será deletado!");
	}
	public ProdutoVinculadoEmPerdaException(String msg){
		super(msg);
	}
	public ProdutoVinculadoEmPerdaException(Throwable t){
		super(t);
	}
}