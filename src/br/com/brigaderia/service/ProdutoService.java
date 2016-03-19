package br.com.brigaderia.service;

import java.sql.Connection;
import java.util.Date;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.jdbc.JDBCProdutoDAO;
import br.com.brigaderia.jdbcinterface.ProdutoDAO;
import br.com.brigaderia.objetos.Produto;

public class ProdutoService {
	
	public int adicionar(Produto produto) throws BrigaderiaException{
		Conexao conec = new Conexao();
		
		try {
			Connection conexao = conec.abrirConexao();
			produto.setDataCadastro(new Date());
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
}
