package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.brigaderia.jdbcinterface.UsuarioDAO;
import br.com.brigaderia.objetos.Usuario;

public class JDBCUsuarioDAO implements UsuarioDAO{
	
	private Connection conexao;
	
	public JDBCUsuarioDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}

	public Usuario buscar(String usuario) throws SQLException {
		
		String comando = "SELECT * FROM USUARIO WHERE EMAIL = '" + usuario +"'";
		Usuario user = new Usuario();
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			user.setId(rs.getInt("ID"));
			user.setNome(rs.getString("NOME"));
			user.setEmail(rs.getString("EMAIL"));
			user.setSenha(rs.getString("SENHA"));
		}
	
	return user;
	}
}