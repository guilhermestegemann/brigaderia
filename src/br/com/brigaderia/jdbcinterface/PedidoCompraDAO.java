package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.objetos.PedidoCompra;

public interface PedidoCompraDAO {
	
	
	public int adicionarPedido(PedidoCompra pedidoCompra) throws BrigaderiaException;
	public void adicionarProdutos(int numeroPedido, int codProduto, float qtde, float unitario, float total) throws BrigaderiaException, SQLException;
	public void countProdutos(int codigo) throws BrigaderiaException;

}
