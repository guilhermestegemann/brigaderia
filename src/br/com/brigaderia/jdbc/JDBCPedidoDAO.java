package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.brigaderia.exception.ClienteComPedidoException;
import br.com.brigaderia.jdbcinterface.PedidoDAO;

public class JDBCPedidoDAO implements PedidoDAO{
	
	private Connection conexao;

	public JDBCPedidoDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public void verificaPedidoCliente (int codigo) throws ClienteComPedidoException {
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
		}catch (ClienteComPedidoException e) {
			throw e;
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
