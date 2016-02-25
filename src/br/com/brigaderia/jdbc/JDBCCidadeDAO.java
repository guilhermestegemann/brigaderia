package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.brigaderia.jdbcinterface.CidadeDAO;
import br.com.brigaderia.objetos.Cidade;
import br.com.brigaderia.objetos.Estado;

public class JDBCCidadeDAO implements CidadeDAO{
	
	private Connection conexao;
	
	public JDBCCidadeDAO (Connection conexao){
		this.conexao = conexao;
	}
	
	public List<Cidade> buscar() {
		
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
				int codigo = rs.getInt("CODIGO");
				String nome = rs.getString("NOME");
				int codigoEstado = rs.getInt("CODIGOESTADO");
				String nomeEstado = rs.getString("NOMEESTADO");
				String ufEstado = rs.getString("UFESTADO");
				
				cidade.setCodigo(codigo);
				cidade.setNome(nome);
				cidade.setEstado(new Estado(codigoEstado, nomeEstado, ufEstado));
				listCidade.add(cidade);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}return listCidade;
	}

}
