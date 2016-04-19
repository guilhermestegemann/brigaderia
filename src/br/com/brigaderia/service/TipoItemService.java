package br.com.brigaderia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.jdbc.JDBCTipoItemDAO;
import br.com.brigaderia.jdbcinterface.TipoItemDAO;
import br.com.brigaderia.objetos.TipoItem;

public class TipoItemService {
	
	
	public List<TipoItem> buscar() throws SQLException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			TipoItemDAO jdbcTipoItem = new JDBCTipoItemDAO(conexao);
			return jdbcTipoItem.buscar();
		}finally {
			conec.fecharConexao();
		}
	}
}
