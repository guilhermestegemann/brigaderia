package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;

import br.com.brigaderia.exception.ProdutoVinculadoEmOrdemProducaoException;
import br.com.brigaderia.objetos.OrdemProducao;

public interface OrdemProducaoDAO {
	
	public void countProdutos(int codigo) throws SQLException, ProdutoVinculadoEmOrdemProducaoException;
	public int adicionarOrdemProducao(OrdemProducao ordemProducao) throws SQLException;
	public void adicionarProdutos(int numeroOrdem, int codProduto, float qtde) throws SQLException;

}
