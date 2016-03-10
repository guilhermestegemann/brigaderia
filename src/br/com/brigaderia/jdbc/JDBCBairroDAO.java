package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.brigaderia.jdbcinterface.BairroDAO;
import br.com.brigaderia.objetos.Bairro;

public class JDBCBairroDAO implements BairroDAO{
	
	private Connection conexao;
	
	
	
	public JDBCBairroDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}



	public List<Bairro> buscar() {
		
		String comando = "SELECT * FROM BAIRRO ";
		List<Bairro> listBairro = new ArrayList<Bairro>();
		Bairro bairro = null;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()) {
				bairro = new Bairro();
				bairro.setCodigo(rs.getInt("codigo"));
				bairro.setNome(rs.getString("nome"));
				listBairro.add(bairro);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}return listBairro;
		
	}

}
