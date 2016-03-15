package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.jdbcinterface.ProdutoDAO;
import br.com.brigaderia.objetos.IngredientesVO;

public class JDBCProdutoDAO implements ProdutoDAO{
	
	private Connection conexao;
	
	public JDBCProdutoDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public List<IngredientesVO> buscarIngredientes () throws BrigaderiaException {
		String comando = "SELECT * FROM PRODUTO WHERE TIPOITEM = 2 ";
		List<IngredientesVO> listIngredientes = new ArrayList<IngredientesVO>();
		IngredientesVO ingredientes = null;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()) {
				ingredientes = new IngredientesVO();
				ingredientes.setCodigo(rs.getInt("codigo"));
				ingredientes.setDescricao(rs.getString("descricao"));
				listIngredientes.add(ingredientes);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BrigaderiaException();
		}return listIngredientes;
	}

}
