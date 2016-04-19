package br.com.brigaderia.exception;

public class ProdutoVinculadoEmPedidoVendaException extends BrigaderiaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProdutoVinculadoEmPedidoVendaException(){
		super("Esse produto possui vínculo com Pedidos de Venda! Por isso não será deletado!");
	}
	public ProdutoVinculadoEmPedidoVendaException(String msg){
		super(msg);
	}
	public ProdutoVinculadoEmPedidoVendaException(Throwable t){
		super(t);
	}
}