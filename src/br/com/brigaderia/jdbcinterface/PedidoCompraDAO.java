package br.com.brigaderia.jdbcinterface;

import br.com.brigaderia.exception.BrigaderiaException;

public interface PedidoCompraDAO {
	
	public void countProdutos(int codigo) throws BrigaderiaException;

}
