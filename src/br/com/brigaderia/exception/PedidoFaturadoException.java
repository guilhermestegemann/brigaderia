package br.com.brigaderia.exception;

public class PedidoFaturadoException extends BrigaderiaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PedidoFaturadoException(){
		super("Não foi possível concluir pois o pedido encontra-se faturado.");
	}
	public PedidoFaturadoException(String msg){
		super(msg);
	}
	public PedidoFaturadoException(Throwable t){
		super(t);
	}
}