package br.com.brigaderia.service;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.jdbc.JDBCProdutoDAO;
import br.com.brigaderia.jdbc.JDBCVendasDAO;
import br.com.brigaderia.jdbcinterface.ProdutoDAO;
import br.com.brigaderia.jdbcinterface.VendasDAO;
import br.com.brigaderia.objetos.DadosClientesVO;
import br.com.brigaderia.objetos.Produto;
import br.com.brigaderia.objetos.VendaPorClienteVO;
import br.com.brigaderia.objetos.VendaPorProdutoVO;

public class VendaService {
	
	public List<DadosClientesVO> listarClientes() throws SQLException {
		
		ClienteService cliService = new ClienteService();
		return cliService.buscarClientesVO("null", "S");

	}
	
	public List<Produto> buscarProdutos() throws SQLException {
		
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			return jdbcProduto.buscarProdutos("null", "S", "1,3");
		}finally {
			conec.fecharConexao();
		}
	}
	
	public List<VendaPorProdutoVO> gerarPorProduto(String dataInicio, String dataFim, int produto, int numReg) throws SQLException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			VendasDAO jdbcVenda = new JDBCVendasDAO(conexao);
			return jdbcVenda.gerarPorProduto(dataInicio, dataFim, produto, numReg);
		}finally{
			conec.fecharConexao();
		}
	}
	
	public List<VendaPorClienteVO> gerarPorCliente(String dataInicio, String dataFim, int cliente, int numReg) throws SQLException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			VendasDAO jdbcVenda = new JDBCVendasDAO(conexao);
			return jdbcVenda.gerarPorCliente(dataInicio, dataFim, cliente, numReg);
		}finally{
			conec.fecharConexao();
		}
	}

}
