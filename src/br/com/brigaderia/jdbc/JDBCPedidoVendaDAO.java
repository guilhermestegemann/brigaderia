package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.ClienteComPedidoException;
import br.com.brigaderia.exception.ProdutoVinculadoEmPedidoVendaException;
import br.com.brigaderia.jdbcinterface.PedidoVendaDAO;

public class JDBCPedidoVendaDAO implements PedidoVendaDAO{
	
	private Connection conexao;

	public JDBCPedidoVendaDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public void verificaPedidoCliente (int codigo) throws BrigaderiaException {
		String comando = "SELECT COUNT(*) AS QTDEPEDIDOS FROM PEDIDO  WHERE PEDIDO.CLIENTE = " + codigo;
		int qtdeClientes = 0;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()) {
				qtdeClientes = rs.getInt("QTDEPEDIDOS");
			}
			if (qtdeClientes > 0) {
				throw new ClienteComPedidoException();
			}
		}catch (BrigaderiaException e) {
			e.printStackTrace();
			throw e;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BrigaderiaException();
		}
	}
	
	public void countProdutos(int codigo) throws BrigaderiaException {
		String comando = "SELECT COUNT(*) AS QTDEPRODUTO FROM ITEMPEDIDO  WHERE ITEMPEDIDO.PRODUTO = " + codigo;
		int qtdeProduto = 0;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()) {
				qtdeProduto = rs.getInt("QTDEPRODUTO");
			}
			if (qtdeProduto > 0) {
				throw new ProdutoVinculadoEmPedidoVendaException();
			}
		}catch (BrigaderiaException e) {
			e.printStackTrace();
			throw e;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BrigaderiaException();
		}
	}

}
