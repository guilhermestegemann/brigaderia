package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.objetos.Bairro;

public interface BairroDAO {
	
	public List<Bairro> buscar() throws SQLException;

}
