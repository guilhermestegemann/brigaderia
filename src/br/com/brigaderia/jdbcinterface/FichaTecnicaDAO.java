package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.exception.IngredienteVinculadoEmFichaTecnicaException;
import br.com.brigaderia.exception.SomarCustoFichaTecnicaException;
import br.com.brigaderia.objetos.FichaTecnica;
import br.com.brigaderia.objetos.ItemFichaTecnica;

public interface FichaTecnicaDAO {
	
	public int adicionar(FichaTecnica fichaTecnica) throws SQLException;
	public void adicionarIngredientes(int codFichaTecnica, int codIngrediente, float qtde) throws SomarCustoFichaTecnicaException, SQLException;
	public void atualizar (FichaTecnica fichaTecnica) throws SQLException;
	public FichaTecnica buscarPeloCodigoProduto(int codigoProduto) throws SQLException;
	public void deletarIngredientes(int codFicha) throws SQLException;
	public void countIngredientes(int codigo) throws SQLException, IngredienteVinculadoEmFichaTecnicaException;
	public void atualizarCustoFichaTecnica(int codIngrediente) throws SQLException;
	public List<ItemFichaTecnica> buscarQtdeIngrediente (int numOrdem) throws SQLException;
	public float buscarCustoPeloProduto(int codigoProduto) throws SQLException;
}
