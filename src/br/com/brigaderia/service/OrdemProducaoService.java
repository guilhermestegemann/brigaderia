package br.com.brigaderia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.jdbc.JDBCOrdemProducaoDAO;
import br.com.brigaderia.jdbc.JDBCPedidoVendaDAO;
import br.com.brigaderia.jdbc.JDBCPerdaDAO;
import br.com.brigaderia.jdbc.JDBCProdutoDAO;
import br.com.brigaderia.jdbcinterface.OrdemProducaoDAO;
import br.com.brigaderia.jdbcinterface.PedidoVendaDAO;
import br.com.brigaderia.jdbcinterface.PerdaDAO;
import br.com.brigaderia.jdbcinterface.ProdutoDAO;
import br.com.brigaderia.objetos.ItemOrdemProducao;
import br.com.brigaderia.objetos.ItemPerda;
import br.com.brigaderia.objetos.OrdemProducao;
import br.com.brigaderia.objetos.PedidoVenda;
import br.com.brigaderia.objetos.Perda;
import br.com.brigaderia.objetos.Produto;
import br.com.brigaderia.validacoes.ValidaOrdemProducao;
import br.com.brigaderia.validacoes.ValidaPerda;

public class OrdemProducaoService {
	
	public void adicionarOrdemProducao (OrdemProducao ordemProducao) throws BrigaderiaException, SQLException {
		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		try {
			
			conexao.setAutoCommit(false);
			ValidaOrdemProducao validaOrdem = new ValidaOrdemProducao();
			validaOrdem.validar(ordemProducao);
			ordemProducao.setData(new Date());
			OrdemProducaoDAO jdbcOrdemProducao = new JDBCOrdemProducaoDAO(conexao);
			
			
			ordemProducao.setNumero(jdbcOrdemProducao.adicionarOrdemProducao(ordemProducao));
			List<ItemOrdemProducao> listProdutos = new ArrayList<>();
			listProdutos = ordemProducao.getItemOrdemProducao();
			
			for (ItemOrdemProducao itemOrdemProducao : listProdutos) {
				jdbcOrdemProducao.adicionarProdutos(ordemProducao.getNumero(), itemOrdemProducao.getCodigoProduto(), itemOrdemProducao.getQtde());
			}
			conexao.commit();
		}catch (BrigaderiaException e) {
			conexao.rollback();
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			conexao.rollback();
			e.printStackTrace();
			throw new BrigaderiaException();
		}finally {
			conec.fecharConexao();
		}
	}
	
	public List<Produto> buscarProdutos() throws SQLException {
		
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			return jdbcProduto.buscarProdutos("null", "S", "1");
		}finally {
			conec.fecharConexao();
		}
	}
	
	public List<PedidoVenda> buscarPedidosImportacao() throws SQLException {
		 Conexao conec = new Conexao();
		 try {
			 Connection conexao = conec.abrirConexao();
			 PedidoVendaDAO jdbcPedidoVenda = new JDBCPedidoVendaDAO(conexao);
			 return jdbcPedidoVenda.buscarPedidosImportacao();
		 }finally{
			 conec.fecharConexao();
		 }
	}
/*
	public List<Perda> buscarPerdas (String dataIni, String dataFim) throws SQLException {
		
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			PerdaDAO jdbcPerda = new JDBCPerdaDAO(conexao);
			return jdbcPerda.buscarPerdas(dataIni, dataFim);
		}finally{
			conec.fecharConexao();
		}
	}
		
	public Perda buscarPerdaPeloNumero (int numero) throws SQLException {
		
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			PerdaDAO jdbcPerda = new JDBCPerdaDAO(conexao);
			Perda perda = jdbcPerda.buscarPeloNumero(numero);
			perda.setItemPerda(jdbcPerda.buscarItensPerda(perda.getNumero()));
			return perda;
		}finally{
			conec.fecharConexao();
		}	
	}
	
		
	public void deletarPerda (int numero) throws SQLException {
		
		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		try {
			conexao.setAutoCommit(false);
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			PerdaDAO jdbcPerda = new JDBCPerdaDAO(conexao);
			List<ItemPerda> listItemPerda = new ArrayList<ItemPerda>();
			listItemPerda = jdbcPerda.buscarItensPerda(numero);
			
			
			for (ItemPerda itemPerda : listItemPerda) {
				jdbcProduto.movimentaEstoque(itemPerda.getCodigoProduto(), itemPerda.getQtde());
			}
			jdbcPerda.deletarPerda(numero);	
			conexao.commit();
		}catch(SQLException e){
			e.printStackTrace();
			conexao.rollback();
			throw e;
		}
		finally {
			conec.fecharConexao();
		}
	}
	*/
}