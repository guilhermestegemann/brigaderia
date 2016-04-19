package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.objetos.IngredientesVO;
import br.com.brigaderia.objetos.Produto;

public interface ProdutoDAO {
	
	public List<IngredientesVO> buscarIngredientes () throws SQLException;
	public List<Produto> buscarProdutos (String valorBusca, String ativo, String tipoItem) throws SQLException;
	public int adicionar(Produto produto) throws SQLException;
	public void atualizar (Produto produto) throws SQLException;
	public Produto buscarPeloCodigo(int codigo) throws SQLException;
	public void deletar(int codigo) throws SQLException;
	public float retornaCusto (int codProduto) throws SQLException;
	public float retornaEstoque (int codProduto) throws SQLException;
	public float retornaValorVenda (int codProduto) throws SQLException;
	public void atualizarEstoque(int codProduto, float qtde, float unitario, float margem) throws SQLException;
	public void retiraEstoque (int codProduto, float qtde) throws SQLException;

}
