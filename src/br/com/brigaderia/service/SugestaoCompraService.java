package br.com.brigaderia.service;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.jdbc.JDBCSugestaoCompraDAO;
import br.com.brigaderia.jdbcinterface.SugestaoCompraDAO;
import br.com.brigaderia.objetos.DadosClientesVO;
import br.com.brigaderia.objetos.SugestaoCompraVO;

public class SugestaoCompraService {
	
	public List<DadosClientesVO> listarClientes() throws SQLException {
		
		ClienteService cliService = new ClienteService();
		return cliService.buscarClientesVO("null");

	}
	
	public List<SugestaoCompraVO> gerarSugestao(int cliente, String dataInicio, String dataFim) throws SQLException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			SugestaoCompraDAO jdbcSugestao = new JDBCSugestaoCompraDAO(conexao);
			return jdbcSugestao.gerarSugestao(cliente, dataInicio, dataFim);
		}finally{
			conec.fecharConexao();
		}
	}

}
