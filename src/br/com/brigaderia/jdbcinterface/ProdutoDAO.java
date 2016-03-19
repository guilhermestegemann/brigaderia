package br.com.brigaderia.jdbcinterface;

import java.util.List;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.objetos.IngredientesVO;
import br.com.brigaderia.objetos.Produto;


public interface ProdutoDAO {
	
	public List<IngredientesVO> buscarIngredientes () throws BrigaderiaException;
	public int adicionar(Produto produto) throws BrigaderiaException;

}
