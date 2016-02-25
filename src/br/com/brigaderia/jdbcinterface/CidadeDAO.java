package br.com.brigaderia.jdbcinterface;

import java.util.List;

import br.com.brigaderia.objetos.Cidade;

public interface CidadeDAO {
	
	public List<Cidade> buscar();

}
