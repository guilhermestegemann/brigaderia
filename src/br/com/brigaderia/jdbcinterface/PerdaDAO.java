package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.exception.ProdutoVinculadoEmPerdaException;
import br.com.brigaderia.objetos.ItemPerda;
import br.com.brigaderia.objetos.Perda;

public interface PerdaDAO {
	
	public void countProdutos(int codigo) throws SQLException, ProdutoVinculadoEmPerdaException;
	public int adicionarPerda(Perda perda) throws SQLException;
	public void adicionarProdutos(int numeroPerda, int codProduto, float qtde, float unitario, float total) throws SQLException;
	public List<ItemPerda> buscarItensPerda(int numero) throws SQLException;
	public List<Perda> buscarPerdas(String dataIni, String dataFim) throws SQLException;
	public Perda buscarPeloNumero(int numero) throws SQLException;
	public void deletarPerda(int numero) throws SQLException;
}
