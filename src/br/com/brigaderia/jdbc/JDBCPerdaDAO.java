package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.ProdutoVinculadoEmPerdaException;
import br.com.brigaderia.jdbcinterface.PerdaDAO;

public class JDBCPerdaDAO implements PerdaDAO{
	
	private Connection conexao;

	public JDBCPerdaDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public void countProdutos(int codigo) throws SQLException, ProdutoVinculadoEmPerdaException  {
		String comando = "SELECT COUNT(*) AS QTDEPRODUTO FROM ITEMPERDA  WHERE ITEMPERDA.PRODUTO = " + codigo;
		int qtdeProduto = 0;
		
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			qtdeProduto = rs.getInt("QTDEPRODUTO");
		}
		if (qtdeProduto > 0) {
			throw new ProdutoVinculadoEmPerdaException();
		}
	}
}
