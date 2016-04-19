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
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			pedidoCompra.setNumero(jdbcPedidoCompra.adicionarPedido(pedidoCompra));
			List<ItemPedidoCompra> listProdutos = new ArrayList<>();
			listProdutos = pedidoCompra.getItemPedidoCompra();
			
			int codProduto;
			float qtde, unitario, total, custo, estoque, valorVenda, novoCusto, margem;
		
			for(int i = 0; i < listProdutos.size(); i++) {
				
				codProduto = listProdutos.get(i).getCodigoProduto();
				qtde = listProdutos.get(i).getQtde();
				unitario = listProdutos.get(i).getUnitario();
				total = listProdutos.get(i).getTotal();
				custo = jdbcProduto.retornaCusto(codProduto);
				estoque = jdbcProduto.retornaEstoque(codProduto);
				valorVenda = jdbcProduto.retornaValorVenda(codProduto);
				novoCusto = 0;
				margem = 0;
				jdbcPedidoCompra.adicionarProdutos(pedidoCompra.getNumero(), codProduto, qtde, unitario, total);
				
				if (estoque <= 0) {
					if(unitario > 0) {
						novoCusto = unitario;	
					}
				}else{
					novoCusto =((custo * estoque) + (unitario * qtde))/(estoque + qtde);
					
				}
				
				if (novoCusto > 0) {
					margem = ((valorVenda / novoCusto)-1)*100;
				}
				
				jdbcProduto.atualizarEstoque(codProduto, qtde, novoCusto, margem);
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
	
	public List<Produto> buscarProdutos() throws SQLException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			return jdbcProduto.buscarProdutos("null", "S", "2,3");
		}finally {
			conec.fecharConexao();
		}
	}
	
	public List<PedidoCompra> buscarPedidoCompra (String dataIni, String dataFim) throws SQLException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			PedidoCompraDAO jdbcPedidoCompra = new JDBCPedidoCompraDAO(conexao);
			return jdbcPedidoCompra.buscarPedidos(dataIni, dataFim);
		}finally{
			conec.fecharConexao();
		}
	}
	
	public PedidoCompra buscarPedidoPeloNumero (int numero) throws SQLException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			PedidoCompraDAO jdbcPedidoCompra = new JDBCPedidoCompraDAO(conexao);
			PedidoCompra pedidoCompra = jdbcPedidoCompra.buscarPeloNumero(numero);
			pedidoCompra.setItemPedidoCompra(jdbcPedidoCompra.buscarItensPedido(pedidoCompra.getNumero()));
			return pedidoCompra;
		}finally{
			conec.fecharConexao();
		}	
	}
	
	
	public String deletarPedido (int numero) throws SQLException {
		Conexao conec = new Conexao();
		String msg = "";
		try {
			Connection conexao = conec.abrirConexao();
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			PedidoCompraDAO jdbcPedidoCompra = new JDBCPedidoCompraDAO(conexao);
			List<ItemPedidoCompra> listItemPedido = new ArrayList<ItemPedidoCompra>();
			listItemPedido = jdbcPedidoCompra.buscarItensPedido(numero);
			float estoque, qtde;
			
			for (int i = 0; i < listItemPedido.size(); i++) {
				estoque = listItemPedido.get(i).getEstoque();
				qtde = listItemPedido.get(i).getQtde();
				if (estoque < qtde) {
					if (msg.equals("")){
						msg = "<h>Produtos com inconsistência ao excluir Pedido de Compra.</h></br>";
					}
					msg += "Código: " + listItemPedido.get(i).getCodigoProduto() + " Descrição: " + listItemPedido.get(i).getDescricao()
						 + " Estoque: " + estoque + " Quantidade: " + qtde + "<br>";	
				}
			}
			if (msg.equals("")) {
				jdbcPedidoCompra.deletarPedido(numero);
				for (int i = 0; i < listItemPedido.size(); i++) {
					jdbcProduto.retiraEstoque(listItemPedido.get(i).getCodigoProduto(), listItemPedido.get(i).getQtde());
				}
				
				msg = "Pedido deletado com sucesso";
			}
			
		}finally {
			conec.fecharConexao();
		}
		return msg;
	}
	

}
