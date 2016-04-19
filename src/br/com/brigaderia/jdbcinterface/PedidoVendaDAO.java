package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.ClienteComPedidoException;
import br.com.brigaderia.exception.ProdutoVinculadoEmPedidoVendaException;

public interface PedidoVendaDAO {
	
	public void verificaPedidoCliente (int codigo) throws SQLException, ClienteComPedidoException;
	public void countProdutos(int codigo) throws SQLException, ProdutoVinculadoEmPedidoVendaException;
}
