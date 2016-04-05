package br.com.brigaderia.jdbcinterface;

import java.util.List;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.objetos.IngredientesVO;
import br.com.brigaderia.objetos.Produto;


public interface ProdutoDAO {
	
	public List<IngredientesVO> buscarIngredientes () throws BrigaderiaException;
	public List<Produto> buscarProdutos (String valorBusca, String ativo, String tipoItem) throws BrigaderiaException;
	public int adicionar(Produto produto) throws BrigaderiaException;
	public void atualizar (Produto produto) throws BrigaderiaException;
	public Produto buscarPeloCodigo(int codigo) throws BrigaderiaException;
	public void deletar(int codigo) throws BrigaderiaException;

}
