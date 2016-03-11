package br.com.brigaderia.service;

import java.sql.Connection;
import java.util.List;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.jdbc.JDBCBairroDAO;
import br.com.brigaderia.jdbcinterface.BairroDAO;
import br.com.brigaderia.objetos.Bairro;

public class BairroService {
	
	public List<Bairro> buscar() throws BrigaderiaException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			BairroDAO jdbcBairro = new JDBCBairroDAO(conexao);
			return jdbcBairro.buscar();
		}catch (BrigaderiaException e) {
			throw e;
		}finally {
			conec.fecharConexao();
		}
	}
}
