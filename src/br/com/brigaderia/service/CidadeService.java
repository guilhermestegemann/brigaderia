package br.com.brigaderia.service;

import java.sql.Connection;
import java.util.List;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.jdbc.JDBCCidadeDAO;
import br.com.brigaderia.jdbcinterface.CidadeDAO;
import br.com.brigaderia.objetos.Cidade;

public class CidadeService {
	
	public List<Cidade> buscar () throws BrigaderiaException{
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			CidadeDAO jdbcCidade = new JDBCCidadeDAO(conexao);
			return jdbcCidade.buscar();
		}catch (BrigaderiaException e) {
			throw e;
		}finally {
			conec.fecharConexao();
		}
	}

}
