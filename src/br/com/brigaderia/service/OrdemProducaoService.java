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
import br.com.brigaderia.exception.OrdemNaoProduzidaException;
import br.com.brigaderia.exception.OrdemProduzidaException;
import br.com.brigaderia.jdbc.JDBCFichaTecnicaDAO;
import br.com.brigaderia.jdbc.JDBCOrdemProducaoDAO;
import br.com.brigaderia.jdbc.JDBCProdutoDAO;
import br.com.brigaderia.jdbcinterface.FichaTecnicaDAO;
import br.com.brigaderia.jdbcinterface.OrdemProducaoDAO;
import br.com.brigaderia.jdbcinterface.ProdutoDAO;
import br.com.brigaderia.objetos.ItemFichaTecnica;
import br.com.brigaderia.objetos.ItemOrdemProducao;
import br.com.brigaderia.objetos.OrdemProducao;
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
	
	public String iniciarProducao (int numero) throws SQLException, BrigaderiaException {
		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		String msg = "";
		try {
			conexao.setAutoCommit(false);
			OrdemProducaoDAO jdbcOrdem = new JDBCOrdemProducaoDAO(conexao);
			if (jdbcOrdem.emProducao(numero)) {
				throw new OrdemEmProducaoException();
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
					 + " | Estoque: " + itemFichaTecnica.getEstoque() + " | Quantidade: " + itemFichaTecnica.getQtde() + " | Falta: "+ (itemFichaTecnica.getQtde() - itemFichaTecnica.getEstoque()) +" <br>";
				}
			}
			if (msg.equals("")){
				for (ItemFichaTecnica itemFichaTecnica : listIngredientes) {
					jdbcProduto.movimentaEstoque(itemFichaTecnica.getCodigoProduto(), (itemFichaTecnica.getQtde()* -1));
				}
				Date hoje  = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				jdbcOrdem.setarEmProducao(numero, sdf.format(hoje));
				msg = "Ordem de Produção número " + numero + " iniciada";
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
	
	public void finalizarProducao (int numero) throws SQLException, BrigaderiaException {
		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		try{
			conexao.setAutoCommit(false);
			OrdemProducaoDAO jdbcOrdem = new JDBCOrdemProducaoDAO(conexao);
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			FichaTecnicaDAO jdbcFichatecnica = new JDBCFichaTecnicaDAO(conexao);
			if (!jdbcOrdem.emProducao(numero)) {
				throw new OrdemNaoEmProducaoException();
			}
			if(jdbcOrdem.produzida(numero)) {
				throw new OrdemProduzidaException();
			}
			
			List<ItemOrdemProducao> listProdutos = new ArrayList<>();
			listProdutos = jdbcOrdem.buscarItensOrdem(numero);
			float custo;
			float novoCusto = 0;
			for (ItemOrdemProducao itemOrdemProducao : listProdutos) {
				custo = jdbcFichatecnica.buscarCustoPeloProduto(itemOrdemProducao.getCodigoProduto());
				if (itemOrdemProducao.getEstoque() <= 0) {
					if(custo > 0) {
						novoCusto = custo;	
					}
				}else{
					novoCusto =((itemOrdemProducao.getValorCusto() * itemOrdemProducao.getEstoque()) + (custo * itemOrdemProducao.getQtde()))/(itemOrdemProducao.getEstoque() + itemOrdemProducao.getQtde());
					
				}
				jdbcProduto.atualizarCusto(itemOrdemProducao.getCodigoProduto(), novoCusto);
				jdbcProduto.atualizarMargem(itemOrdemProducao.getCodigoProduto(), ((itemOrdemProducao.getValorVenda() / novoCusto)-1)*100);
				jdbcProduto.movimentaEstoque(itemOrdemProducao.getCodigoProduto(), itemOrdemProducao.getQtde());
			}
			Date hoje  = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			jdbcOrdem.setarProduzida(numero, sdf.format(hoje));
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
	
	
	
	public void cancelarFinalizada (int numero) throws SQLException, BrigaderiaException{
		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		try{
			conexao.setAutoCommit(false);
			OrdemProducaoDAO jdbcOrdem = new JDBCOrdemProducaoDAO(conexao);
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			if(!jdbcOrdem.produzida(numero)){
				throw new OrdemNaoProduzidaException();
			}
			List<ItemOrdemProducao> listProdutos = new ArrayList<>();
			listProdutos = jdbcOrdem.buscarItensOrdem(numero);
			for (ItemOrdemProducao itemOrdemProducao : listProdutos) {
				jdbcProduto.movimentaEstoque(itemOrdemProducao.getCodigoProduto(), (itemOrdemProducao.getQtde()* -1));
			}
			jdbcOrdem.cancelarProduzida(numero);
			conexao.commit();
		}catch (BrigaderiaException e){
			conexao.rollback();
			e.printStackTrace();
			throw e;
		}catch(SQLException e){
			conexao.rollback();
			e.printStackTrace();
		}finally{
			conec.fecharConexao();
		}
	}
	
			
	public void deletarOrdem (int numero) throws SQLException, BrigaderiaException {
		
		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		try {
			OrdemProducaoDAO jdbcOrdem = new JDBCOrdemProducaoDAO(conexao);
			if (jdbcOrdem.emProducao(numero)){
				throw new OrdemEmProducaoException();
			}
			if (jdbcOrdem.produzida(numero)){
				throw new OrdemProduzidaException();
			}
			jdbcOrdem.deletarOrdem(numero);	
		}catch(BrigaderiaException e){
			e.printStackTrace();
			throw e;
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			conec.fecharConexao();
		}
	}
	
}