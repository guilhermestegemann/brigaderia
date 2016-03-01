package br.com.brigaderia.jdbcinterface;

import br.com.brigaderia.exception.ClienteComPedidoException;



public interface PedidoDAO {
	
	public void verificaPedidoCliente (int codigo) throws ClienteComPedidoException;
}
