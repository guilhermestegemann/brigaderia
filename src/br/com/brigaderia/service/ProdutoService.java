package br.com.brigaderia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.jdbc.JDBCFichaTecnicaDAO;
import br.com.brigaderia.jdbc.JDBCOrdemProducaoDAO;
import br.com.brigaderia.jdbc.JDBCPedidoCompraDAO;
import br.com.brigaderia.jdbc.JDBCPedidoVendaDAO;
import br.com.brigaderia.jdbc.JDBCPerdaDAO;
import br.com.brigaderia.jdbc.JDBCProdutoDAO;
import br.com.brigaderia.jdbcinterface.FichaTecnicaDAO;
import br.com.brigaderia.jdbcinterface.OrdemProducaoDAO;
import br.com.brigaderia.jdbcinterface.PedidoCompraDAO;
import br.com.brigaderia.jdbcinterface.PedidoVendaDAO;
import br.com.brigaderia.jdbcinterface.PerdaDAO;
import br.com.brigaderia.jdbcinterface.ProdutoDAO;
import br.com.brigaderia.objetos.Produto;
import br.com.brigaderia.validacoes.ValidaProduto;

public class ProdutoService {
	
	public int adicionar(Produto produto) throws SQLException, BrigaderiaException{
		Conexao conec = new Conexao();
		
		try {
			Connection conexao = conec.abrirConexao();
			produto.setDataCadastro(new Date());
			ValidaProduto validaProduto = new ValidaProduto();
			validaProduto.validarProduto(produto);
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			return jdbcProduto.adicionar(produto);
		}finally{
			conec.fecharConexao();
		}
	}
	
	public Produto buscarProdutoPeloCodigo (int codigo) throws SQLException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			Produto produto = jdbcProduto.buscarPeloCodigo(codigo);
			return produto;
		}finally{
			conec.fecharConexao();
		}	
	}
	
	public void deletarProduto (int codigo) throws SQLException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			jdbcProduto.deletar(codigo);
		}finally{
			conec.fecharConexao();
		}
	}
	
	public List<Produto> buscarProdutos (String valorBusca, String ativo, String tipoItem) throws SQLException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			return jdbcProduto.buscarProdutos(valorBusca, ativo, tipoItem);
		}finally{
			conec.fecharConexao();
		}
	}
	
	public void atualizarProduto (Produto produto) throws BrigaderiaException, SQLException  {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			ValidaProduto validaProduto = new ValidaProduto();
			validaProduto.validarProduto(produto);
			jdbcProduto.atualizar(produto);
		}finally{
			conec.fecharConexao();
		}
	}
	
	public void deletarProduto (int codigo, int tipoItem) throws BrigaderiaException, SQLException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			PedidoVendaDAO jdbcPedidoVenda = new JDBCPedidoVendaDAO(conexao);
			PedidoCompraDAO jdbcPedidoCompra = new JDBCPedidoCompraDAO(conexao);
			PerdaDAO jdbcPerda = new JDBCPerdaDAO(conexao);
			jdbcPerda.countProdutos(codigo);
			if (tipoItem == 1) {
				jdbcPedidoVenda.countProdutos(codigo);
				OrdemProducaoDAO jdbcOrdemProducao = new JDBCOrdemProducaoDAO(conexao);
				jdbcOrdemProducao.countProdutos(codigo);
			}else if (tipoItem == 2) {
				jdbcPedidoCompra.countProdutos(codigo);
				FichaTecnicaDAO jdbcFichaTecnica = new JDBCFichaTecnicaDAO(conexao);
				jdbcFichaTecnica.countIngredientes(codigo);
			}else{
				jdbcPedidoVenda.countProdutos(codigo);
				jdbcPedidoCompra.countProdutos(codigo);
			}
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			jdbcProduto.deletar(codigo);
		}finally {
			conec.fecharConexao();
		}
	}
}
