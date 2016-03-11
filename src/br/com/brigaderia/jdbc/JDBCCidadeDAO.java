package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.jdbcinterface.CidadeDAO;
import br.com.brigaderia.objetos.Cidade;
import br.com.brigaderia.objetos.Estado;

public class JDBCCidadeDAO implements CidadeDAO{
	
	private Connection conexao;
	
	public JDBCCidadeDAO (Connection conexao){
		this.conexao = conexao;
	}
	
	public List<Cidade> buscar() throws BrigaderiaException{
		
		String comando = "SELECT CIDADE.CODIGO, CIDADE.NOME, ESTADO.CODIGO AS CODIGOESTADO, ESTADO.NOME AS NOMEESTADO, ESTADO.UF AS UFESTADO "
			       + "FROM CIDADE "
			       + "INNER JOIN ESTADO ON ESTADO.CODIGO = CIDADE.ESTADO "
			       + "ORDER BY CIDADE.NOME";
		List<Cidade> listCidade = new ArrayList<Cidade>();
		Cidade cidade = null;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while (rs.next()) {
				cidade = new Cidade();
				cidade.setCodigo(rs.getInt("CODIGO"));
				cidade.setNome(rs.getString("NOME"));
				cidade.setEstado(new Estado(rs.getInt("CODIGOESTADO"), rs.getString("NOMEESTADO"), rs.getString("UFESTADO")));
				listCidade.add(cidade);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BrigaderiaException();
		}return listCidade;
	}

}
