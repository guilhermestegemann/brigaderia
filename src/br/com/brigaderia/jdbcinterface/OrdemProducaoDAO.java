package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.exception.ProdutoVinculadoEmOrdemProducaoException;
import br.com.brigaderia.objetos.ItemOrdemProducao;
import br.com.brigaderia.objetos.OrdemProducao;

public interface OrdemProducaoDAO {
	
	public void countProdutos(int codigo) throws SQLException, ProdutoVinculadoEmOrdemProducaoException;
	public int adicionarOrdemProducao(OrdemProducao ordemProducao) throws SQLException;
	public void adicionarProdutos(int numeroOrdem, int codProduto, float qtde) throws SQLException;
	public List<OrdemProducao> buscarOrdens (String dataInicio, String dataFim, String status) throws SQLException;
	public OrdemProducao buscarPeloNumero (int numero) throws SQLException;
	public List<ItemOrdemProducao> buscarItensOrdem(int numero) throws SQLException;
	public void deletarProdutos(int numero) throws SQLException;
	public boolean emProducao (int numero) throws SQLException;
	public void setarEmProducao (int numero, String dataInicio) throws SQLException;
	public boolean estaCancelada (int numero) throws SQLException;
	public void cancelarProducao (int numero) throws SQLException;

}
