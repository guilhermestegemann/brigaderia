package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.objetos.TipoItem;

public interface TipoItemDAO {
	
	public List<TipoItem> buscar() throws SQLException;

}
