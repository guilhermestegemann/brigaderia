package br.com.brigaderia.exception;

public class ProdutoVinculadoEmPedidoCompraException extends BrigaderiaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProdutoVinculadoEmPedidoCompraException(){
		super("Esse produto possui vínculo com Pedidos de Compra! Por isso não será deletado!");
	}
	public ProdutoVinculadoEmPedidoCompraException(String msg){
		super(msg);
	}
	public ProdutoVinculadoEmPedidoCompraException(Throwable t){
		super(t);
	}
}