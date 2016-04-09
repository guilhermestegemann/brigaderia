package br.com.brigaderia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.jdbc.JDBCPedidoCompraDAO;
import br.com.brigaderia.jdbc.JDBCProdutoDAO;
import br.com.brigaderia.jdbcinterface.PedidoCompraDAO;
import br.com.brigaderia.jdbcinterface.ProdutoDAO;
import br.com.brigaderia.objetos.ItemPedidoCompra;
import br.com.brigaderia.objetos.PedidoCompra;
import br.com.brigaderia.objetos.Produto;
import br.com.brigaderia.validacoes.ValidaPedidoCompra;

public class PedidoCompraService {
	
	public void adicionarPedido (PedidoCompra pedidoCompra) throws BrigaderiaException, SQLException {
		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		try {
			
			conexao.setAutoCommit(false);
			pedidoCompra.setTotal(pedidoCompra.getTotal());
			ValidaPedidoCompra validaPedidoCompra = new ValidaPedidoCompra();
			validaPedidoCompra.validar(pedidoCompra);
			pedidoCompra.setData(new Date());
			PedidoCompraDAO jdbcPedidoCompra = new JDBCPedidoCompraDAO(conexao);
			pedidoCompra.setNumero(jdbcPedidoCompra.adicionarPedido(pedidoCompra));
			List<ItemPedidoCompra> listProdutos = new ArrayList<>();
			listProdutos = pedidoCompra.getItemPedidoCompra();
			for(int i = 0; i < listProdutos.size(); i++) {
				jdbcPedidoCompra.adicionarProdutos(pedidoCompra.getNumero(), listProdutos.get(i).getCodigoProduto(), listProdutos.get(i).getQtde(), 
												   listProdutos.get(i).getUnitario(), listProdutos.get(i).getTotal());
				jdbcPedidoCompra.atualizarEstoque(listProdutos.get(i).getCodigoProduto(), listProdutos.get(i).getQtde(), listProdutos.get(i).getUnitario());
			}
			conexao.commit();
		}catch (BrigaderiaException e) {
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
	
	public List<Produto> buscarProdutos() throws BrigaderiaException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			return jdbcProduto.buscarProdutos("null", "S", "2,3");
		}catch (BrigaderiaException e) {
			throw e;
		}finally {
			conec.fecharConexao();
		}
	}
	
	public List<PedidoCompra> buscarPedidoCompra (String dataIni, String dataFim) throws BrigaderiaException{
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			PedidoCompraDAO jdbcPedidoCompra = new JDBCPedidoCompraDAO(conexao);
			return jdbcPedidoCompra.buscarPedidos(dataIni, dataFim);
		}catch (Exception e) {
			throw e;
		}finally{
			conec.fecharConexao();
		}
	}

}
