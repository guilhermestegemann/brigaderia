package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;

import br.com.brigaderia.exception.ProdutoVinculadoEmOrdemProducaoException;

public interface OrdemProducaoDAO {
	
	public void countProdutos(int codigo) throws SQLException, ProdutoVinculadoEmOrdemProducaoException;

}
