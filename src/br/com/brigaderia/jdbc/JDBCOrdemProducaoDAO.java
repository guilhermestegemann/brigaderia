package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.brigaderia.exception.ProdutoVinculadoEmOrdemProducaoException;
import br.com.brigaderia.jdbcinterface.OrdemProducaoDAO;
import br.com.brigaderia.objetos.OrdemProducao;

public class JDBCOrdemProducaoDAO implements OrdemProducaoDAO {
	
	private Connection conexao;

	public JDBCOrdemProducaoDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public void countProdutos(int codigo) throws SQLException, ProdutoVinculadoEmOrdemProducaoException {
		String comando = "SELECT PRODUTO FROM ITEMORDEMPRODUCAO  WHERE ITEMORDEMPRODUCAO.PRODUTO = " + codigo + " LIMIT 1";
		
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		if (rs.next()) {
			throw new ProdutoVinculadoEmOrdemProducaoException();
		}
	}
	
	public int adicionarOrdemProducao(OrdemProducao ordemProducao) throws SQLException {
		
		String comando = "INSERT INTO ORDEMPRODUCAO (DATA, EMPRODUCAO, PRODUZIDA, CANCELADA) VALUES (?,?,?,?)";
		PreparedStatement p;
		

		p = this.conexao.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS);
		p.setDate(1, new java.sql.Date(ordemProducao.getData().getTime()));
		p.setString(2, "N");
		p.setString(3, "N");
		p.setString(4, "N");
		p.execute();
		ResultSet generatedKeys = p.getGeneratedKeys();
        if (generatedKeys.next()) {
        	ordemProducao.setNumero(generatedKeys.getInt(1));
        }
        else {
            throw new SQLException("Erro ao recuperar chave inserida! (Ordem Producao)");
        }
		return ordemProducao.getNumero();
	}
	
	public void adicionarProdutos(int numeroOrdemProducao, int codProduto, float qtde) throws SQLException{
		String comando = "INSERT INTO ITEMORDEMPRODUCAO (ORDEMPRODUCAO, PRODUTO, QTDE) VALUES (?,?,?)";
		PreparedStatement p;
		try{
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, numeroOrdemProducao);
			p.setInt(2, codProduto);
			p.setFloat(3, qtde);
			p.execute();
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}
	}
}