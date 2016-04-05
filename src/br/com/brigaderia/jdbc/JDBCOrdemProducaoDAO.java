package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.ProdutoVinculadoEmOrdemProducaoException;
import br.com.brigaderia.jdbcinterface.OrdemProducaoDAO;

public class JDBCOrdemProducaoDAO implements OrdemProducaoDAO {
	
	
	private Connection conexao;

	public JDBCOrdemProducaoDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public void countProdutos(int codigo) throws BrigaderiaException {
		String comando = "SELECT COUNT(*) AS QTDEPRODUTO FROM ITEMORDEMPRODUCAO  WHERE ITEMORDEMPRODUCAO.PRODUTO = " + codigo;
		int qtdeProduto = 0;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()) {
				qtdeProduto = rs.getInt("QTDEPRODUTO");
			}
			if (qtdeProduto > 0) {
				throw new ProdutoVinculadoEmOrdemProducaoException();
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