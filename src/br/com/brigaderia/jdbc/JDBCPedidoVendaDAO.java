package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.brigaderia.exception.ClienteComPedidoException;
import br.com.brigaderia.exception.ProdutoVinculadoEmPedidoVendaException;
import br.com.brigaderia.jdbcinterface.PedidoVendaDAO;

public class JDBCPedidoVendaDAO implements PedidoVendaDAO{
	
	private Connection conexao;

	public JDBCPedidoVendaDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public void verificaPedidoCliente (int codigo) throws SQLException, ClienteComPedidoException  {
		String comando = "SELECT CLIENTE FROM PEDIDO  WHERE PEDIDO.CLIENTE = " + codigo + " LIMIT 1";
		
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		if (rs.next()) {
			throw new ClienteComPedidoException();
		}
	}
	
	public void countProdutos(int codigo) throws SQLException, ProdutoVinculadoEmPedidoVendaException  {
		String comando = "SELECT PRODUTO FROM ITEMPEDIDO  WHERE ITEMPEDIDO.PRODUTO = " + codigo + " LIMIT 1";
		
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		if (rs.next()) {
			throw new ProdutoVinculadoEmPedidoVendaException();
		}
	}
}