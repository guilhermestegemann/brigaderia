package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.jdbcinterface.TipoItemDAO;
import br.com.brigaderia.objetos.TipoItem;

public class JDBCTipoItemDAO implements TipoItemDAO{
	
	private Connection conexao;
	
	public JDBCTipoItemDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}

	public List<TipoItem> buscar() throws BrigaderiaException{
		
		String comando = "SELECT * FROM TIPOITEM ";
		List<TipoItem> listTipoItem = new ArrayList<TipoItem>();
		TipoItem tipoItem = null;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()) {
				tipoItem = new TipoItem();
				tipoItem.setCodigo(rs.getInt("codigo"));
				tipoItem.setTipo(rs.getString("tipo"));
				listTipoItem.add(tipoItem);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BrigaderiaException();
		}return listTipoItem;
		
	}

}
