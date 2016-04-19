package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.brigaderia.exception.ProdutoVinculadoEmOrdemProducaoException;
import br.com.brigaderia.jdbcinterface.OrdemProducaoDAO;

public class JDBCOrdemProducaoDAO implements OrdemProducaoDAO {
	
	private Connection conexao;

	public JDBCOrdemProducaoDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public void countProdutos(int codigo) throws SQLException, ProdutoVinculadoEmOrdemProducaoException {
		String comando = "SELECT COUNT(*) AS QTDEPRODUTO FROM ITEMORDEMPRODUCAO  WHERE ITEMORDEMPRODUCAO.PRODUTO = " + codigo;
		int qtdeProduto = 0;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			qtdeProduto = rs.getInt("QTDEPRODUTO");
		}
		if (qtdeProduto > 0) {
			throw new ProdutoVinculadoEmOrdemProducaoException();
		}
	}
}