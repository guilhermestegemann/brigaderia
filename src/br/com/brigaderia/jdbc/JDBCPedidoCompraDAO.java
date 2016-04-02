package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.ProdutoVinculadoEmPedidoCompraException;
import br.com.brigaderia.jdbcinterface.PedidoCompraDAO;

public class JDBCPedidoCompraDAO implements PedidoCompraDAO {
	
	private Connection conexao;

	public JDBCPedidoCompraDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public void countProdutos(int codigo) throws BrigaderiaException {
		String comando = "SELECT COUNT(*) AS QTDEPRODUTO FROM ITEMCOMPRA  WHERE ITEMCOMPRA.PRODUTO = " + codigo;
		int qtdeProduto = 0;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()) {
				qtdeProduto = rs.getInt("QTDEPRODUTO");
			}
			if (qtdeProduto > 0) {
				throw new ProdutoVinculadoEmPedidoCompraException();
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
