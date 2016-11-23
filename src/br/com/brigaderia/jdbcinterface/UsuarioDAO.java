package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;

import br.com.brigaderia.objetos.Usuario;

public interface UsuarioDAO {
	
	public Usuario buscar(String usuario) throws SQLException;

}
