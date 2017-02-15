package br.com.brigaderia.service;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.jdbc.JDBCGraficoDAO;
import br.com.brigaderia.jdbcinterface.GraficoDAO;
import br.com.brigaderia.objetos.VendaAnualVO;
import br.com.brigaderia.objetos.VendaPorProdutoVO;

public class GraficoService {
	
	public List<VendaAnualVO> vendaAnual(int ano) throws SQLException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			GraficoDAO jdbcGrafico = new JDBCGraficoDAO(conexao);
			return jdbcGrafico.vendaAnual(ano);
		}finally{
			conec.fecharConexao();
		}
	}
	
	public List<VendaPorProdutoVO> gerarPorProduto(String dataInicio, String dataFim, String orderBy, int numReg) throws SQLException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			GraficoDAO jdbcGrafico = new JDBCGraficoDAO(conexao);
			return jdbcGrafico.gerarPorProduto(dataInicio, dataFim, orderBy, numReg);
		}finally{
			conec.fecharConexao();
		}
	}

}
