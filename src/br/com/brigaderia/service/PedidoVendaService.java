package br.com.brigaderia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.PedidoFaturadoException;
import br.com.brigaderia.jdbc.JDBCClienteDAO;
import br.com.brigaderia.jdbc.JDBCPedidoVendaDAO;
import br.com.brigaderia.jdbc.JDBCProdutoDAO;
import br.com.brigaderia.jdbcinterface.ClienteDAO;
import br.com.brigaderia.jdbcinterface.PedidoVendaDAO;
import br.com.brigaderia.jdbcinterface.ProdutoDAO;
import br.com.brigaderia.objetos.DadosClientesVO;
import br.com.brigaderia.objetos.ItemPedidoVenda;
import br.com.brigaderia.objetos.PedidoVenda;
import br.com.brigaderia.objetos.PedidoVendaVO;
import br.com.brigaderia.objetos.Produto;
import br.com.brigaderia.validacoes.ValidaPedidoVenda;

public class PedidoVendaService {
	
	public void adicionarPedido (PedidoVenda pedidoVenda) throws BrigaderiaException, SQLException {
		
		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		try {
			
			conexao.setAutoCommit(false);
			ValidaPedidoVenda validaPedidoVenda = new ValidaPedidoVenda();
			validaPedidoVenda.validar(pedidoVenda);
			pedidoVenda.setDataEmissao(new Date());
			PedidoVendaDAO jdbcPedidoVenda = new JDBCPedidoVendaDAO(conexao);
			pedidoVenda.setNumero(jdbcPedidoVenda.adicionarPedido(pedidoVenda));
			List<ItemPedidoVenda> listProdutos = new ArrayList<>();
			listProdutos = pedidoVenda.getItemPedidoVenda();
			
			for (ItemPedidoVenda itemPedidoVenda : listProdutos) {

				jdbcPedidoVenda.adicionarProdutos(pedidoVenda.getNumero(), itemPedidoVenda.getCodigoProduto(), itemPedidoVenda.getQtde(),
						                          itemPedidoVenda.getUnitario(), itemPedidoVenda.getTotal());
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
	
	public void editarPedido(PedidoVenda pedidoVenda) throws SQLException, BrigaderiaException {
		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		try {
			conexao.setAutoCommit(false);
			ValidaPedidoVenda validaPedidoVenda = new ValidaPedidoVenda();
			validaPedidoVenda.validar(pedidoVenda);
			PedidoVendaDAO jdbcPedidoVenda = new JDBCPedidoVendaDAO(conexao);
			jdbcPedidoVenda.deletarProdutos(pedidoVenda.getNumero());
			jdbcPedidoVenda.editarPedido(pedidoVenda);
			List<ItemPedidoVenda> listProdutos = new ArrayList<>();
			listProdutos = pedidoVenda.getItemPedidoVenda();
			
			for (ItemPedidoVenda itemPedidoVenda : listProdutos) {
				jdbcPedidoVenda.adicionarProdutos(pedidoVenda.getNumero(), itemPedidoVenda.getCodigoProduto(), itemPedidoVenda.getQtde(),
									              itemPedidoVenda.getUnitario(), itemPedidoVenda.getTotal());
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
			return jdbcProduto.buscarProdutos("null", "S", "1,3");
		}finally {
			conec.fecharConexao();
		}
	}
	
	public List<DadosClientesVO> buscarClientes() throws SQLException {
		
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ClienteDAO jdbcCliente = new JDBCClienteDAO(conexao);
			return jdbcCliente.buscarClientes("null", "S");
		}finally {
			conec.fecharConexao();
		}
	}
	
	public List<PedidoVendaVO> buscarPedidoVenda (String dataIni, String dataFim, String faturado, String cancelado,
			                                      String produzido, int codCliente) throws SQLException {
		
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			PedidoVendaDAO jdbcPedidoVenda = new JDBCPedidoVendaDAO(conexao);
			return jdbcPedidoVenda.buscarPedidos(dataIni, dataFim, faturado, cancelado, produzido, codCliente);
		}finally{
			conec.fecharConexao();
		}
	}
	
	public PedidoVenda buscarPedidoPeloNumero (int numero) throws SQLException {
		
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			PedidoVendaDAO jdbcPedidoVenda = new JDBCPedidoVendaDAO(conexao);
			PedidoVenda pedidoVenda = jdbcPedidoVenda.buscarPeloNumero(numero);
			pedidoVenda.setItemPedidoVenda(jdbcPedidoVenda.buscarItensPedido(pedidoVenda.getNumero()));
			return pedidoVenda;
		}finally{
			conec.fecharConexao();
		}	
	}
	
	public String faturarPedido (int numero) throws SQLException, PedidoFaturadoException {
		Conexao conec = new Conexao();
		String msg = "";
		try {
			Connection conexao = conec.abrirConexao();
			PedidoVendaDAO jdbcPedidoVenda = new JDBCPedidoVendaDAO(conexao);
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			if (jdbcPedidoVenda.pedidoFaturado(numero)) {
				throw new PedidoFaturadoException();
			}
			List<ItemPedidoVenda> listItemPedido = new ArrayList<ItemPedidoVenda>();
			listItemPedido = jdbcPedidoVenda.buscarItensPedido(numero);
			
			for (ItemPedidoVenda itemPedidoVenda : listItemPedido) {
				
				if (itemPedidoVenda.getEstoque() < itemPedidoVenda.getQtde()) {
					if (msg.equals("")){
						msg = "Estoque insuficiente para os seguinte produtos: \n";
					}
					msg += "Código: " + itemPedidoVenda.getCodigoProduto() + " | Descrição: " + itemPedidoVenda.getDescricao()
						 + " | Estoque: " + itemPedidoVenda.getEstoque() + " | Quantidade: " + itemPedidoVenda.getQtde() + " | Falta: "+ (itemPedidoVenda.getQtde() - itemPedidoVenda.getEstoque()) +" <br>";	
				}
			}
			if (msg.equals("")) {
				for (ItemPedidoVenda itemPedidoVenda : listItemPedido) {
					jdbcProduto.movimentaEstoque(itemPedidoVenda.getCodigoProduto(), (itemPedidoVenda.getQtde() *-1));
				}
				
				
				jdbcPedidoVenda.faturarPedido(numero, new Date());
				msg = "Pedido faturado com sucesso!";
			}
		}finally{
			conec.fecharConexao();
		}
		return msg;
	}
	
	public void cancelarPedido (int numero) throws SQLException {
		Conexao conec = new Conexao();
		try{
			Connection conexao = conec.abrirConexao();
			PedidoVendaDAO jdbcPedidoVenda = new JDBCPedidoVendaDAO(conexao);
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			if (jdbcPedidoVenda.pedidoFaturado(numero)) {
				List<ItemPedidoVenda> itemPedido = new ArrayList<>();
				itemPedido = jdbcPedidoVenda.buscarItensPedido(numero);
				for (ItemPedidoVenda itemPedidoVenda : itemPedido) {
					jdbcProduto.movimentaEstoque(itemPedidoVenda.getCodigoProduto(), itemPedidoVenda.getQtde());
				}
			}
			jdbcPedidoVenda.cancelarPedido(numero);
		}finally{
			conec.fecharConexao();
		}
	}
	
	public void descancelarPedido (int numero) throws SQLException {
		Conexao conec = new Conexao();
		try{
			Connection conexao = conec.abrirConexao();
			PedidoVendaDAO jdbcPedidoVenda = new JDBCPedidoVendaDAO(conexao);
			jdbcPedidoVenda.descancelarPedido(numero);
		}finally{
			conec.fecharConexao();
		}
	}
	

	public void deletarPedido (int numero) throws SQLException, PedidoFaturadoException {
		
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			PedidoVendaDAO jdbcPedidoVenda = new JDBCPedidoVendaDAO(conexao);
			if (jdbcPedidoVenda.pedidoFaturado(numero)){
				throw new PedidoFaturadoException();
			}else{
				jdbcPedidoVenda.deletarPedido(numero);
			}
			
		}finally {
			conec.fecharConexao();
		}
	}
}