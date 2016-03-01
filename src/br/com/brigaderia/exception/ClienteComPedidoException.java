package br.com.brigaderia.exception;

public class ClienteComPedidoException extends BrigaderiaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ClienteComPedidoException(){
		super("Esse cliente possui pedido! Por isso não será deletado!");
	}
	public ClienteComPedidoException(String msg){
		super(msg);
	}
	public ClienteComPedidoException(Throwable t){
		super(t);
	}
	

}
