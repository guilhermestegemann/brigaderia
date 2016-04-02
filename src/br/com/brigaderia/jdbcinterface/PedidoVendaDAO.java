package br.com.brigaderia.jdbcinterface;

import br.com.brigaderia.exception.BrigaderiaException;

public interface PedidoVendaDAO {
	
	public void verificaPedidoCliente (int codigo) throws BrigaderiaException;
	public void countProdutos(int codigo) throws BrigaderiaException;
}
