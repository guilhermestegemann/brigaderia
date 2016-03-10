package br.com.brigaderia.jdbcinterface;


import br.com.brigaderia.exception.BrigaderiaException;



public interface PedidoDAO {
	
	public void verificaPedidoCliente (int codigo) throws BrigaderiaException;
}
