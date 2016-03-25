package br.com.brigaderia.jdbcinterface;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.objetos.FichaTecnica;

public interface FichaTecnicaDAO {
	
	public int adicionar(FichaTecnica fichaTecnica) throws BrigaderiaException;
	public void adicionarIngredientes(int codFichaTecnica, int codIngrediente, float qtde) throws BrigaderiaException;
	public FichaTecnica buscarPeloCodigoProduto(int codigoProduto) throws BrigaderiaException;
}
