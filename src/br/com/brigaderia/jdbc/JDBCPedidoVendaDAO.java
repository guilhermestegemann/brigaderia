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
		String comando = "SELECT COUNT(*) AS QTDEPEDIDOS FROM PEDIDO  WHERE PEDIDO.CLIENTE = " + codigo;
		int qtdeClientes = 0;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			qtdeClientes = rs.getInt("QTDEPEDIDOS");
		}
		if (qtdeClientes > 0) {
			throw new ClienteComPedidoException();
		}
	}
	
	public void countProdutos(int codigo) throws SQLException, ProdutoVinculadoEmPedidoVendaException  {
		String comando = "SELECT COUNT(*) AS QTDEPRODUTO FROM ITEMPEDIDO  WHERE ITEMPEDIDO.PRODUTO = " + codigo;
		int qtdeProduto = 0;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			qtdeProduto = rs.getInt("QTDEPRODUTO");
		}
		if (qtdeProduto > 0) {
			throw new ProdutoVinculadoEmPedidoVendaException();
		}
	}
}