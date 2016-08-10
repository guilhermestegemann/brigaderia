package br.com.brigaderia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.jdbc.JDBCPerdaDAO;
import br.com.brigaderia.jdbc.JDBCProdutoDAO;
import br.com.brigaderia.jdbcinterface.PerdaDAO;
import br.com.brigaderia.jdbcinterface.ProdutoDAO;
import br.com.brigaderia.objetos.ItemPerda;
import br.com.brigaderia.objetos.Perda;
import br.com.brigaderia.objetos.Produto;
import br.com.brigaderia.validacoes.ValidaPerda;

public class PerdaService {
	
	public String adicionarPerda (Perda perda) throws BrigaderiaException, SQLException {
		String msg = "";
		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		try {
			
			conexao.setAutoCommit(false);
			ValidaPerda validaPerda = new ValidaPerda();
			validaPerda.validar(perda);
			perda.setData(new Date());
			PerdaDAO jdbcPerda = new JDBCPerdaDAO(conexao);
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			perda.setNumero(jdbcPerda.adicionarPerda(perda));
			List<ItemPerda> listProdutos = new ArrayList<>();
			listProdutos = perda.getItemPerda();
			
			for (ItemPerda itemPerda : listProdutos) {
				jdbcPerda.adicionarProdutos(perda.getNumero(), itemPerda.getCodigoProduto(), itemPerda.getQtde(),
								            itemPerda.getUnitario(), itemPerda.getTotal());
			}
			
			List<ItemPerda> listaItemPerda = new ArrayList<ItemPerda>();
			listaItemPerda = jdbcPerda.buscarItensPerda(perda.getNumero());
			
			for (ItemPerda itemPerda : listaItemPerda) {
				
				if (itemPerda.getEstoque() < itemPerda.getQtde()) {
					if (msg.equals("")){
						msg = "Estoque insuficiente para os seguinte produtos:.\n";
					}
					msg += "Código: " + itemPerda.getCodigoProduto() + " | Descrição: " + itemPerda.getDescricao()
						 + " | Estoque: " + itemPerda.getEstoque() + " | Quantidade: " + itemPerda.getQtde() + "<br>";	
				}
			}
			if (msg.equals("")) {
				for (ItemPerda itemPerda : listaItemPerda) {
					jdbcProduto.movimentaEstoque(itemPerda.getCodigoProduto(), (itemPerda.getQtde() *-1));
				}
				msg = "Perda lançada com sucesso!";
			}else{
				conexao.rollback();	
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
		return msg;
	}
	
	public List<Produto> buscarProdutos() throws SQLException {
		
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			return jdbcProduto.buscarProdutos("null", "S", "1,2,3");
		}finally {
			conec.fecharConexao();
		}
	}

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
	
}