package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;

import br.com.brigaderia.exception.IngredienteVinculadoEmFichaTecnicaException;
import br.com.brigaderia.exception.SomarCustoFichaTecnicaException;
import br.com.brigaderia.objetos.FichaTecnica;

public interface FichaTecnicaDAO {
	
	public int adicionar(FichaTecnica fichaTecnica) throws SQLException;
	public void adicionarIngredientes(int codFichaTecnica, int codIngrediente, float qtde) throws SomarCustoFichaTecnicaException, SQLException;
	public void atualizar (FichaTecnica fichaTecnica) throws SQLException;
	public FichaTecnica buscarPeloCodigoProduto(int codigoProduto) throws SQLException;
	public void deletarIngredientes(int codFicha) throws SQLException;
	public void countIngredientes(int codigo) throws SQLException, IngredienteVinculadoEmFichaTecnicaException;
}
