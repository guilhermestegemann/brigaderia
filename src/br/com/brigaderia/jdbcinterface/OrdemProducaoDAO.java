package br.com.brigaderia.jdbcinterface;

import br.com.brigaderia.exception.BrigaderiaException;

public interface OrdemProducaoDAO {
	
	public void countProdutos(int codigo) throws BrigaderiaException;

}
