package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.exception.ProdutoVinculadoEmPedidoCompraException;
import br.com.brigaderia.objetos.ItemPedidoCompra;
import br.com.brigaderia.objetos.PedidoCompra;

public interface PedidoCompraDAO {
	
	public int adicionarPedido(PedidoCompra pedidoCompra) throws SQLException;
	public void adicionarProdutos(int numeroPedido, int codProduto, float qtde, float unitario, float total, float qtdeMultiplaEntrada);
	public void countProdutos(int codigo) throws SQLException, ProdutoVinculadoEmPedidoCompraException;
	public List<PedidoCompra> buscarPedidos(String dataIni, String dataFim) throws SQLException;
	public PedidoCompra buscarPeloNumero(int numero) throws SQLException;
	public List<ItemPedidoCompra> buscarItensPedido(int numero) throws SQLException;
	public void deletarPedido(int numero) throws SQLException;
	

}
