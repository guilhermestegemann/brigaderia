package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.ProdutoVinculadoEmPerdaException;

public interface PerdaDAO {
	
	public void countProdutos(int codigo) throws SQLException, ProdutoVinculadoEmPerdaException;
}
