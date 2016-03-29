package br.com.brigaderia.service;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.jdbc.JDBCProdutoDAO;
import br.com.brigaderia.jdbcinterface.ProdutoDAO;
import br.com.brigaderia.objetos.Produto;
import br.com.brigaderia.validacoes.ValidaProduto;

public class ProdutoService {
	
	public int adicionar(Produto produto) throws BrigaderiaException{
		Conexao conec = new Conexao();
		
		try {
			Connection conexao = conec.abrirConexao();
			produto.setDataCadastro(new Date());
			ValidaProduto validaProduto = new ValidaProduto();
			validaProduto.validarProduto(produto);
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			return jdbcProduto.adicionar(produto);
		}catch (BrigaderiaException e) {
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			throw new BrigaderiaException();
		}finally{
			conec.fecharConexao();
		}
	}
	
	public Produto buscarProdutoPeloCodigo (int codigo) throws BrigaderiaException{
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			Produto produto = jdbcProduto.buscarPeloCodigo(codigo);
			return produto;
		}catch (BrigaderiaException e) {
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			throw new BrigaderiaException();
		}
		finally{
			conec.fecharConexao();
		}	
	}
	
	public void deletarProduto (int codigo) throws BrigaderiaException{
		Conexao conec = new Conexao();
		
		try {
			Connection conexao = conec.abrirConexao();
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			jdbcProduto.deletar(codigo);
		}catch(BrigaderiaException e) {
			e.printStackTrace();
			throw e;
		}finally{
			conec.fecharConexao();
		}
	}
	
	public List<Produto> buscarProdutos (String valorBusca, String ativo) throws BrigaderiaException{
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			return jdbcProduto.buscarProdutos(valorBusca, ativo);
		}catch (Exception e) {
			throw e;
		}finally{
			conec.fecharConexao();
		}
	}
	
	public void atualizarProduto (Produto produto) throws BrigaderiaException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			ValidaProduto validaProduto = new ValidaProduto();
			validaProduto.validarProduto(produto);
			jdbcProduto.atualizar(produto);
		}catch (BrigaderiaException e) {
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			throw new BrigaderiaException();
		}finally{
			conec.fecharConexao();
		}
	}
}
