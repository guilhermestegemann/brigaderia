package br.com.brigaderia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.OrdemEmProducaoException;
import br.com.brigaderia.exception.OrdemNaoEmProducaoException;
import br.com.brigaderia.exception.OrdemProducaoCanceladaException;
import br.com.brigaderia.jdbc.JDBCFichaTecnicaDAO;
import br.com.brigaderia.jdbc.JDBCOrdemProducaoDAO;
import br.com.brigaderia.jdbc.JDBCPedidoVendaDAO;
import br.com.brigaderia.jdbc.JDBCProdutoDAO;
import br.com.brigaderia.jdbcinterface.FichaTecnicaDAO;
import br.com.brigaderia.jdbcinterface.OrdemProducaoDAO;
import br.com.brigaderia.jdbcinterface.PedidoVendaDAO;
import br.com.brigaderia.jdbcinterface.ProdutoDAO;
import br.com.brigaderia.objetos.ItemFichaTecnica;
import br.com.brigaderia.objetos.ItemOrdemProducao;
import br.com.brigaderia.objetos.OrdemProducao;
import br.com.brigaderia.objetos.PedidoVenda;
import br.com.brigaderia.objetos.Produto;
import br.com.brigaderia.validacoes.ValidaOrdemProducao;

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

	public List<OrdemProducao> buscarOrdens (String dataIni, String dataFim, String status) throws SQLException {
		
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			OrdemProducaoDAO jdbcOrdemProducao = new JDBCOrdemProducaoDAO(conexao);
			return jdbcOrdemProducao.buscarOrdens(dataIni, dataFim, status);
		}finally{
			conec.fecharConexao();
		}
	}
		
	public OrdemProducao buscarOrdemPeloNumero (int numero) throws SQLException {
		
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			OrdemProducaoDAO jdbcOrdem = new JDBCOrdemProducaoDAO(conexao);
			OrdemProducao ordem = jdbcOrdem.buscarPeloNumero(numero);
			ordem.setItemOrdemProducao(jdbcOrdem.buscarItensOrdem(ordem.getNumero()));
			return ordem;
		}finally{
			conec.fecharConexao();
		}	
	}
	
	public void editarOrdemProducao (OrdemProducao ordem) throws SQLException, BrigaderiaException {
		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		try {
			conexao.setAutoCommit(false);
			ValidaOrdemProducao validaOrdem = new ValidaOrdemProducao();
			validaOrdem.validar(ordem);
			OrdemProducaoDAO jdbcOrdemProducao = new JDBCOrdemProducaoDAO(conexao);
			jdbcOrdemProducao.deletarProdutos(ordem.getNumero());
			List<ItemOrdemProducao> listProdutos = new ArrayList<>();
			listProdutos = ordem.getItemOrdemProducao();
			
			for (ItemOrdemProducao itemOrdemProducao : listProdutos) {
				jdbcOrdemProducao.adicionarProdutos(ordem.getNumero(), itemOrdemProducao.getCodigoProduto(), itemOrdemProducao.getQtde());
			}
			conexao.commit();
		}catch (BrigaderiaException e){
			conexao.rollback();
			e.printStackTrace();
			throw e;
		}catch (SQLException e){
			conexao.rollback();
			e.printStackTrace();
			throw new BrigaderiaException();
		}finally {
			conec.fecharConexao();
		}
	}
	
	public String produzir (int numero) throws SQLException, BrigaderiaException {
		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		String msg = "";
		try {
			conexao.setAutoCommit(false);
			OrdemProducaoDAO jdbcOrdem = new JDBCOrdemProducaoDAO(conexao);
			if (jdbcOrdem.emProducao(numero)) {
				throw new OrdemEmProducaoException();
			}
			if (jdbcOrdem.estaCancelada(numero)) {
				throw new OrdemProducaoCanceladaException();
			}
			List<ItemFichaTecnica> listIngredientes = new ArrayList<>();
			FichaTecnicaDAO jdbcFichaTecnica = new JDBCFichaTecnicaDAO(conexao);
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			listIngredientes = jdbcFichaTecnica.buscarQtdeIngrediente(numero);
			for (ItemFichaTecnica itemFichaTecnica : listIngredientes) {
				if (itemFichaTecnica.getQtde() > itemFichaTecnica.getEstoque()) {
					if (msg.equals("")) {
						msg = "Estoque insuficiente para os seguintes ingredientes: \n";
					}
					msg += "Código: " + itemFichaTecnica.getCodigoProduto() + " | Descrição: " + itemFichaTecnica.getDescricao()
					 + " | Estoque: " + itemFichaTecnica.getEstoque() + " | Quantidade: " + itemFichaTecnica.getQtde() + " <br>";
				}
			}
			if (msg.equals("")){
				for (ItemFichaTecnica itemFichaTecnica : listIngredientes) {
					jdbcProduto.movimentaEstoque(itemFichaTecnica.getCodigoProduto(), (itemFichaTecnica.getQtde()* -1));
				}
				Date hoje  = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				jdbcOrdem.setarEmProducao(numero, sdf.format(hoje));
				msg = "Ordem de Produção " + numero + " iniciada";
			}
			conexao.commit();
		}catch (BrigaderiaException e) {
			conexao.rollback();
			e.printStackTrace();
			throw e;
		}catch(SQLException e){
			conexao.rollback();
			e.printStackTrace();
			throw e;
		}finally {
			conec.fecharConexao();
		}
		return msg;
	}
	
	public void cancelarProducao (int numero) throws SQLException, BrigaderiaException{
		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		try {
			conexao.setAutoCommit(false);
			OrdemProducaoDAO jdbcOrdem = new JDBCOrdemProducaoDAO(conexao);
			if (!jdbcOrdem.emProducao(numero)) {
				throw new OrdemNaoEmProducaoException();
			}
			if (jdbcOrdem.estaCancelada(numero)) {
				throw new OrdemProducaoCanceladaException();
			}
			List<ItemFichaTecnica> listIngredientes = new ArrayList<>();
			FichaTecnicaDAO jdbcFichaTecnica = new JDBCFichaTecnicaDAO(conexao);
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			listIngredientes = jdbcFichaTecnica.buscarQtdeIngrediente(numero);
			for (ItemFichaTecnica itemFichaTecnica : listIngredientes) {
				jdbcProduto.movimentaEstoque(itemFichaTecnica.getCodigoProduto(), itemFichaTecnica.getQtde());
			}
			jdbcOrdem.cancelarProducao(numero);
			conexao.commit();
		}catch (BrigaderiaException e) {
			conexao.rollback();
			e.printStackTrace();
			throw e;
		}catch(SQLException e){
			conexao.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			conec.fecharConexao();
		}
	}
	
	/*		
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