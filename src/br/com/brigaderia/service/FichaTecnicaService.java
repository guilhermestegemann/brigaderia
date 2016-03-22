package br.com.brigaderia.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.jdbc.JDBCFichaTecnicaDAO;
import br.com.brigaderia.jdbc.JDBCProdutoDAO;
import br.com.brigaderia.jdbcinterface.FichaTecnicaDAO;
import br.com.brigaderia.jdbcinterface.ProdutoDAO;
import br.com.brigaderia.objetos.FichaTecnica;
import br.com.brigaderia.objetos.IngredientesVO;
import br.com.brigaderia.objetos.ItemFichaTecnicaVO;


public class FichaTecnicaService {
	
	public List<IngredientesVO> buscarIngredientes() throws BrigaderiaException {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			return jdbcProduto.buscarIngredientes();
		}catch (BrigaderiaException e) {
			throw e;
		}finally {
			conec.fecharConexao();
		}
	}
	
	public void adicionar(FichaTecnica fichaTecnica) throws BrigaderiaException{
		Conexao conec = new Conexao();
		
		try {
			Connection conexao = conec.abrirConexao();
			FichaTecnicaDAO jdbcFichaTecnica = new JDBCFichaTecnicaDAO(conexao);
			int codFichaTecnica = jdbcFichaTecnica.adicionar(fichaTecnica);
			List<ItemFichaTecnicaVO> listIngredientes = new ArrayList<>();
			listIngredientes =	fichaTecnica.getIngredientes();
			
			for(int i = 0; i < listIngredientes.size(); i++) {
				jdbcFichaTecnica.adicionarIngredientes(codFichaTecnica, listIngredientes.get(i).getCodigo(), listIngredientes.get(i).getQtde());
			}
			
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


