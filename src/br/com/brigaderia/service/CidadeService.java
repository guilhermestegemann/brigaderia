package br.com.brigaderia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.jdbc.JDBCCidadeDAO;
import br.com.brigaderia.jdbcinterface.CidadeDAO;
import br.com.brigaderia.objetos.Cidade;

public class CidadeService {
	
	public List<Cidade> buscar () throws SQLException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			CidadeDAO jdbcCidade = new JDBCCidadeDAO(conexao);
			return jdbcCidade.buscar();
		}finally {
			conec.fecharConexao();
		}
	}
}
