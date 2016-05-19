package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.exception.ClienteComPedidoException;
import br.com.brigaderia.exception.ProdutoVinculadoEmPedidoVendaException;
import br.com.brigaderia.objetos.PedidoVenda;
import br.com.brigaderia.objetos.PedidoVendaVO;

public interface PedidoVendaDAO {
	
	public int adicionarPedido(PedidoVenda pedidoVenda) throws SQLException;
	public void adicionarProdutos(int numeroPedido, int codProduto, float qtde, float unitario, float total);
	public void verificaPedidoCliente (int codigo) throws SQLException, ClienteComPedidoException;
	public void countProdutos(int codigo) throws SQLException, ProdutoVinculadoEmPedidoVendaException;
	public List<PedidoVendaVO> buscarPedidos(String dataIni, String dataFim, String faturado, String cancelado,
				                             String produzido, int codCliente) throws SQLException;
}
