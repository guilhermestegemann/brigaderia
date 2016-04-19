package br.com.brigaderia.exception;

public class ProdutoVinculadoEmOrdemProducaoException extends BrigaderiaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProdutoVinculadoEmOrdemProducaoException(){
		super("Esse produto possui vínculo com Ordens de Produção! Por isso não será deletado!");
	}
	public ProdutoVinculadoEmOrdemProducaoException(String msg){
		super(msg);
	}
	public ProdutoVinculadoEmOrdemProducaoException(Throwable t){
		super(t);
	}
}