package br.com.brigaderia.service;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.jdbc.JDBCEstoqueDAO;
import br.com.brigaderia.jdbcinterface.EstoqueDAO;
import br.com.brigaderia.objetos.EstoqueVO;

public class EstoqueService {
	
	public List<EstoqueVO> gerarRelatorio(String tipoItem, String ativo) throws SQLException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			EstoqueDAO jdbcEstoque = new JDBCEstoqueDAO(conexao);
			return jdbcEstoque.gerarRelatorio(tipoItem, ativo);
		}finally{
			conec.fecharConexao();
		}
	}
}
